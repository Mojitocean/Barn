package com.barn.order.app.service;

import com.api.barn.order.dto.OrderCreateDTO;
import com.barn.api.product.dto.SkuInfoDTO;
import com.barn.api.product.dto.StockDeductionDTO;
import com.barn.api.product.service.ProductServiceApi;
import com.barn.core.domain.R;
import com.barn.core.exception.ServerException;
import com.barn.order.domain.entity.Order;
import com.barn.order.domain.entity.OrderItem;
import com.barn.order.domain.repo.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName com.barn.order.app.service
 * 【应用服务】 (Application Service)
 * 职责：
 * 1. 编排业务流程 (参数校验 -> 组装实体 -> 调用RPC -> 落库 -> 发消息)
 * 2. 事务控制
 * 3. 转换 DTO 到 Entity
 * 注意：不包含核心业务规则（如价格计算），那些在 Domain 层
 *
 * @author mj
 * @className OrderAppService
 * @date 2025/11/22
 * @description TODO
 */
@Log4j2
@Service
public class OrderAppService {

    @Autowired
    private OrderRepository orderRepository;

    // 注入商品服务 RPC 接口
    // check=false: 启动时不检查服务提供者是否在线 (防止循环依赖导致启动失败)
    // version: 建议在生产环境中指定版本号，如 version = "1.0.0"
    @DubboReference(check = false)
    private ProductServiceApi productServiceApi;

    /**
     * 创建订单核心流程
     *
     * @param dto 下单请求参数
     * @return 订单号
     */
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderCreateDTO dto) {
        log.info("开始处理下单流程: userId={}", dto.getUserId());

        // ---------------------------------------------------------
        // 1. [Assembler] 初始化聚合根 (Order Entity)
        // ---------------------------------------------------------
        Order order = new Order();
        // 调用领域行为：初始化订单状态、生成单号等
        order.create(dto.getUserId());

        // 准备库存扣减列表，用于后续批量 RPC 调用
        List<StockDeductionDTO> deductionList = new ArrayList<>();

        // ---------------------------------------------------------
        // 2. [RPC & Domain] 循环处理订单项
        // ---------------------------------------------------------
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new ServerException("下单商品列表不能为空");
        }

        for (OrderCreateDTO.OrderItemDTO itemDTO : dto.getItems()) {
            Long skuId = itemDTO.getSkuId();
            Integer count = itemDTO.getCount();

            // 2.1 [RPC] 远程查询商品信息 (获取真实价格、名称和库存)
            // 安全性：必须查库，不能信任前端传来的价格
            R<SkuInfoDTO> skuResult = productServiceApi.getSkuInfo(skuId);

            // RPC 调用结果校验
            if (skuResult == null || skuResult.getCode() == R.FAIL || skuResult.getData() == null) {
                log.error("调用商品服务查询SKU失败: skuId={}, result={}", skuId, skuResult);
                throw new ServerException("商品信息不存在或服务异常: " + skuId);
            }
            SkuInfoDTO skuInfo = skuResult.getData();

            // 2.2 [Fail-Fast] 应用层初步库存校验
            // 虽然 Product 服务扣减时还会校验，但这里拦截可以减少无效 RPC 调用
            if (skuInfo.getStock() < count) {
                throw new ServerException("商品[" + skuInfo.getSkuName() + "]库存不足");
            }

            // 2.3 组装订单明细实体 (Entity)
            // 使用 Builder 模式构建，确保数据完整性
            OrderItem item = OrderItem.builder()
                    .skuId(skuInfo.getId())
                    .count(count)
                    // 关键：使用 RPC 查出来的真实价格，防止前端篡改价格
                    .price(skuInfo.getPrice())
                    .skuName(skuInfo.getSkuName())
                    .build();

            // 2.4 [Domain] 添加到聚合根
            // 这一步会自动触发 Order 内部的总价计算逻辑 (calculatePrice)
            order.addItem(item);

            // 2.5 加入待扣减列表
            deductionList.add(new StockDeductionDTO(skuInfo.getId(), count));
        }

        // ---------------------------------------------------------
        // 3. [RPC] 远程扣减库存 (核心分布式事务点)
        // ---------------------------------------------------------
        // 如果这里失败，抛出异常，Spring 事务管理器会回滚整个 createOrder 方法
        R<Boolean> deductResult = productServiceApi.deductStock(deductionList);

        if (deductResult == null || deductResult.getCode() == R.FAIL) {
            String errorMsg = (deductResult == null) ? "RPC Timeout" : deductResult.getMsg();
            log.warn("库存扣减失败: {}", errorMsg);
            // 抛出业务异常，触发事务回滚
            throw new ServerException("库存扣减失败: " + errorMsg);
        }

        // ---------------------------------------------------------
        // 4. [Repo] 持久化聚合根 (订单落库)
        // ---------------------------------------------------------
        // Repository 会负责保存 Order 主表和 OrderItem 子表
        orderRepository.save(order);

        log.info("订单创建成功, orderSn={}, totalAmount={}", order.getOrderSn(), order.getTotalAmount());

        // ---------------------------------------------------------
        // 5. [Event] (可选) 发送下单成功事件
        // ---------------------------------------------------------
        // 这里可以发送 MQ 消息，用于异步发送短信、清理购物车等解耦逻辑
        // eventPublisher.publish(new OrderCreatedEvent(order));

        return order.getOrderSn();
    }
}