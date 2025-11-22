package com.barn.order.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * packageName com.barn.order.domain.entity
 * 【实体】订单明细 (Order Item)
 * 它依附于 Order 聚合根存在，不能单独被 Repo 保存。
 *
 * @author mj
 * @className OrderItem
 * @date 2025/11/22
 * @description TODO
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long skuId;

    /**
     * 商品名称快照 (下单时的名称，防止后续商品改名)
     */
    private String skuName;

    /**
     * 销售价格快照
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer count;
}