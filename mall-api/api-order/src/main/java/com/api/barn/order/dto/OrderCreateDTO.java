package com.api.barn.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * packageName com.api.order.dto
 * 下单请求 DTO (Data Transfer Object)
 * 注意：这里只包含创建订单所需的最基本参数，不包含业务逻辑
 *
 * @author mj
 * @className OrderCreateDTO
 * @date 2025/11/22
 * @description TODO
 */
@Data
public class OrderCreateDTO implements Serializable {

    /**
     * 用户ID (来自网关解析)
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 收货地址ID
     */
    private Long addressId;

    /**
     * 支付方式 (例如：WECHAT, ALIPAY)
     */
    private String paymentType;

    /**
     * 购买商品列表
     */
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO implements Serializable {
        /**
         * SKU ID (库存单元ID)
         */
        private Long skuId;
        /**
         * 购买数量
         */
        private Integer count;
        /**
         * 下单时的商品价格 (前端传入仅做参考，后端需重新校验)
         */
        private BigDecimal price;
    }
}