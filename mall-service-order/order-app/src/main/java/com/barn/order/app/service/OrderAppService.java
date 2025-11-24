package com.barn.order.app.service;

import com.api.barn.order.dto.OrderCreateDTO;
import com.barn.api.product.dto.SkuInfoDTO;
import com.barn.api.product.dto.StockDeductionDTO;
import com.barn.api.product.service.ProductServiceApi;
import com.barn.api.user.dto.AddressDTO;
import com.barn.api.user.service.UserServiceApi;
import com.barn.core.domain.R;
import com.barn.core.exception.BizException;
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

/**
 * 【应用服务】 (Application Service)
 * 职责：
 * 1. 编排业务流程 (参数校验 -> 组装实体 -> 调用RPC -> 落库 -> 发消息)
 * 2. 事务控制
 * 3. 转换 DTO 到 Entity
 *
 * @author mj
 * @date 2025/11/22
 */
@Log4j2
@Service
public class OrderAppService {

    @Autowired
    private OrderRepository orderRepository;

    // 商品服务 RPC
    @DubboReference(check = false)
    private ProductServiceApi productServiceApi;

    // 用户服务 RPC (新增：用于获取地址快照)
    @DubboReference(check = false)
    private UserServiceApi userServiceApi;

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
        // 初始化基础状态 (单号、状态、时间)
        order.create(dto.getUserId());

        // ---------------------------------------------------------
        // 1.1 [RPC] 获取收货地址快照 (新增逻辑)
        // ---------------------------------------------------------
        if (dto.getAddressId() == null) {
            throw new BizException("收货地址不能为空");
        }
        R<AddressDTO> addressResult = userServiceApi.getAddressById(dto.getAddressId());
        if (addressResult == null || addressResult.getCode() != R.SUCCESS || addressResult.getData() == null) {
            log.error("获取收货地址失败: addressId={}", dto.getAddressId());
            throw new BizException("收货地址不存在或服务异常");
        }
        AddressDTO address = addressResult.getData();
        // 将地址信息“快照”存入订单，防止后续用户修改地址导致历史订单显示错误
        // 注意：这里需要在 Order 实体中补充对应的 setReceiverXxx 方法
        order.setReceiverInfo(address.getName(), address.getPhoneNumber(), address.getProvince(), address.getCity(), address.getRegion(), address.getDetailAddress());

        // ---------------------------------------------------------
        // 2. [RPC & Domain] 循环处理订单项
        // ---------------------------------------------------------
        List<StockDeductionDTO> deductionList = new ArrayList<>();

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new BizException("下单商品列表不能为空");
        }

        for (OrderCreateDTO.OrderItemDTO itemDTO : dto.getItems()) {
            Long skuId = itemDTO.getSkuId();
            Integer count = itemDTO.getCount();

            // 2.1 [RPC] 远程查询商品信息 (获取真实价格、名称和库存)
            R<SkuInfoDTO> skuResult = productServiceApi.getSkuInfo(skuId);

            if (skuResult == null || skuResult.getCode() != R.SUCCESS || skuResult.getData() == null) {
                log.error("查询商品SKU失败: skuId={}, result={}", skuId, skuResult);
                throw new BizException("商品信息不存在: " + skuId);
            }
            SkuInfoDTO skuInfo = skuResult.getData();

            // 2.2 [Fail-Fast] 初步库存校验
            if (skuInfo.getStock() < count) {
                throw new BizException("商品[" + skuInfo.getSkuName() + "]库存不足");
            }

            // 2.3 组装订单明细
            OrderItem item = OrderItem.builder().skuId(skuInfo.getId()).count(count).price(skuInfo.getPrice()) // 使用真实价格
                    .skuName(skuInfo.getSkuName()).build();

            // 2.4 [Domain] 添加到聚合根 (触发总价计算)
            order.addItem(item);

            // 2.5 加入待扣减列表
            deductionList.add(new StockDeductionDTO(skuInfo.getId(), count));
        }

        // ---------------------------------------------------------
        // 3. [RPC] 远程扣减库存 (分布式事务关键点)
        // ---------------------------------------------------------
        R<Boolean> deductResult = productServiceApi.deductStock(deductionList);

        if (deductResult == null || deductResult.getCode() != R.SUCCESS) {
            String errorMsg = (deductResult == null) ? "RPC Timeout" : deductResult.getMsg();
            log.warn("库存扣减失败: {}", errorMsg);
            throw new BizException("库存扣减失败: " + errorMsg);
        }

        // ---------------------------------------------------------
        // 4. [Repo] 持久化聚合根
        // ---------------------------------------------------------
        orderRepository.save(order);

        log.info("订单创建成功, orderSn={}, totalAmount={}", order.getOrderSn(), order.getTotalAmount());

        // 5. [Event] 发送消息 (可选)
        // eventPublisher.publish(new OrderCreatedEvent(order));

        return order.getOrderSn();
    }
}