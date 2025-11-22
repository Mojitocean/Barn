package com.barn.order.domain.entity;


import lombok.Data;

import java.math.BigDecimal;

/**
 * packageName com.barn.order.domain.entity
 * SKU 实体 (充血模型)
 *
 * @author mj
 * @className Sku
 * @date 2025/11/22
 * @description TODO
 */
@Data
public class Sku {
    private Long id;
    private Long spuId;
    private String skuCode;
    private BigDecimal price;
    private Integer stock;
    private String specJson; // 规格: {"color":"red", "size":"L"}

    // --- 业务行为 ---

    /**
     * 检查库存是否充足
     */
    public void checkStock(Integer count) {
        if (this.stock < count) {
            throw new RuntimeException("商品库存不足");
        }
    }

    /**
     * 扣减库存 (业务计算，不是数据库操作)
     */
    public void reduceStock(Integer count) {
        checkStock(count);
        this.stock = this.stock - count;
    }
}