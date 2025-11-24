package com.api.barn.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * packageName com.api.order.dto
 * 下单请求 DTO (Data Transfer Object)
 * 注意：这里只包含创建订单所需的最基本参数，不包含业务逻辑
 * 支持订单拆分
 *
 * @author mj
 * @className OrderCreateDTO
 * @date 2025/11/22
 * @description TODO
 */
@Data
public class OrderCreateDTO implements Serializable {

    /**
     * 支付总金额，用于支付服务校验
     */
    private BigDecimal totalPayAmount;

    /**
     * 下单用户ID
     */
    private Long userId;

    /**
     * 订单分组列表：用于将购物车商品按收货地址进行分组。
     * 最终一个 ShippingGroup 对应数据库中的一笔 Order 记录。
     */
    private List<ShippingGroup> shippingGroups;

    /**
     * 【内部类】运输分组
     */
    @Data
    public static class ShippingGroup implements Serializable {
        /**
         * 必填：收货地址ID
         */
        private Long addressId;
        /**
         * 该地址下的所有商品项
         */
        private List<OrderItemDTO> items;
    }

    /**
     * 【内部类】订单明细项
     */
    @Data
    public static class OrderItemDTO implements Serializable {
        /**
         * 商品 SKU ID
         */
        private Long skuId;
        /**
         * 购买数量
         */
        private Integer count;
    }
}