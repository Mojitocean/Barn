package com.barn.product.domain.entity;


import com.barn.core.exception.ServerException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * packageName com.barn.order.domain.entity
 * 【实体】库存单元 (Stock Keeping Unit)
 * 充血模型：包含库存检查逻辑
 *
 * @author mj
 * @className Sku
 * @date 2025/11/22
 * @description TODO
 */
@Getter
@NoArgsConstructor
public class Sku {
    private Long id;
    private Long spuId;
    private String skuCode;
    private String skuName;
    private BigDecimal price;
    private Integer stock;
    private String specJson; // 规格: {"color":"red", "size":"L"}

    // --- 业务行为 ---

    /**
     * 业务行为：检查库存是否充足
     */
    public void checkStock(Integer count) {
        if (this.stock < count) {
            throw new ServerException("商品[" + this.skuName + "]库存不足，当前剩余:" + this.stock);
        }
    }

    /**
     * 业务行为：内存中预扣减 (用于计算，不直接改库)
     */
    public void reduceStock(Integer count) {
        checkStock(count);
        this.stock = this.stock - count;
    }
}