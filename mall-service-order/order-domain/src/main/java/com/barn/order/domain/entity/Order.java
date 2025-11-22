package com.barn.order.domain.entity;

import com.barn.core.exception.ServerException;
import com.barn.order.domain.valobj.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * packageName com.barn.order.domain.entity
 * 【聚合根】订单实体 (Order Aggregate Root)
 * * DDD 原则：
 * 1. 实体包含业务逻辑 (充血模型)。
 * 2. 只有聚合根可以直接被 Repository 保存。
 * 3. 聚合根保证内部子实体 (OrderItem) 的一致性。
 *
 * @author mj
 * @className Order
 * @date 2025/11/22
 * @description TODO
 */
@Getter // 仅开放 Getter，禁止外部直接 Set 属性，必须通过业务方法修改状态
@Log4j2
@NoArgsConstructor
public class Order {

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单号 (业务主键)
     */
    private String orderSn;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单总额 (计算得出)
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额 (总额 - 优惠 + 运费)
     */
    private BigDecimal payAmount;

    /**
     * 订单状态 (值对象)
     */
    private OrderStatus status;

    /**
     * 订单明细 (聚合内部实体)
     */
    private List<OrderItem> items = new ArrayList<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    // --- 业务行为 (Business Behaviors) ---

    /**
     * 行为：初始化新订单
     * 业务规则：生成单号，状态设为待支付，记录时间
     */
    public void create(Long userId) {
        this.userId = userId;
        // 实际生产中建议使用雪花算法生成
        this.orderSn = UUID.randomUUID().toString().replace("-", "");
        this.status = OrderStatus.UNPAID;
        this.createTime = LocalDateTime.now();
    }

    /**
     * 行为：添加订单项
     * 业务规则：添加商品时，自动重新计算总价
     */
    public void addItem(OrderItem item) {
        if (item == null) return;
        this.items.add(item);
        // 每次添加明细，触发价格重算
        calculatePrice();
    }

    /**
     * 行为：计算订单价格 (核心逻辑)
     * 避免在 Service 层写一堆 BigDecimal 加减法
     */
    private void calculatePrice() {
        this.totalAmount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            // 单项小计 = 单价 * 数量
            BigDecimal subTotal = item.getPrice().multiply(new BigDecimal(item.getCount()));
            this.totalAmount = this.totalAmount.add(subTotal);
        }
        // 简化版：暂无优惠券和运费逻辑，实付 = 总额
        this.payAmount = this.totalAmount;
    }

    /**
     * 行为：支付成功
     */
    public void paymentSuccess() {
        if (this.status != OrderStatus.UNPAID) {
            throw new ServerException("订单状态异常，无法支付");
        }
        this.status = OrderStatus.PAID;
        //("订单[{}]支付成功，状态流转为 PAID", this.orderSn);
    }
}