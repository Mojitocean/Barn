package com.barn.order.domain.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName com.barn.order.domain.valobj
 * 【值对象】订单状态枚举
 *
 * @author mj
 * @className OrderStatus
 * @date 2025/11/22
 * @description TODO
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {
    UNPAID(0, "待支付"),
    PAID(1, "已支付"),
    DELIVERED(2, "已发货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消");

    private final int code;
    private final String desc;
}