package com.barn.order.domain.entity;

import com.barn.core.exception.BizException;
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
 * <p>
 * 聚合根】订单实体 (Order Aggregate Root)
 * 仅开放 Getter，禁止外部直接 Set 属性，必须通过业务方法修改状态
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
@Log4j2
@Getter
@NoArgsConstructor
public class Order {
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单总额
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    /**
     * 订单状态
     */
    private OrderStatus status;

    /**
     * 订单明细
     */
    private List<OrderItem> items = new ArrayList<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    // --- 收货人信息快照 (新增) ---
    private String receiverName;
    private String receiverPhone;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private String receiverDetailAddress;

    // --- 业务行为 (Business Behaviors) ---

    public void create(Long userId) {
        this.userId = userId;
        this.orderSn = UUID.randomUUID().toString().replace("-", "");
        this.status = OrderStatus.UNPAID;
        this.createTime = LocalDateTime.now();
    }

    /**
     * 行为：设置收货人信息 (快照)
     * 下单时将地址信息固化到订单中，防止用户修改地址表影响历史订单
     */
    public void setReceiverInfo(String name, String phone, String province, String city, String region, String detailAddress) {
        this.receiverName = name;
        this.receiverPhone = phone;
        this.receiverProvince = province;
        this.receiverCity = city;
        this.receiverRegion = region;
        this.receiverDetailAddress = detailAddress;
    }

    public void addItem(OrderItem item) {
        if (item == null) return;
        this.items.add(item);
        calculatePrice();
    }

    private void calculatePrice() {
        this.totalAmount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            BigDecimal subTotal = item.getPrice().multiply(new BigDecimal(item.getCount()));
            this.totalAmount = this.totalAmount.add(subTotal);
        }
        this.payAmount = this.totalAmount;
    }

    public void paymentSuccess() {
        if (this.status != OrderStatus.UNPAID) {
            throw new BizException("订单状态异常，无法支付");
        }
        this.status = OrderStatus.PAID;
        log.info("订单[{}]支付成功，状态流转为 PAID", this.orderSn);
    }
}