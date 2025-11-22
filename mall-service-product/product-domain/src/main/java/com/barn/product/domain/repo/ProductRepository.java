package com.barn.product.domain.repo;

import com.barn.product.domain.entity.Sku;

/**
 * packageName com.barn.order.domain.repo
 * 商品库存接口
 *
 * @author mj
 * @className ProductRepository
 * @date 2025/11/22
 * @description TODO
 */
public interface ProductRepository {

    /**
     * 根据商品ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    Sku findSkuById(Long id);

    /**
     * 原子扣减库存
     *
     * @param skuId 商品ID
     * @param count 扣减数量
     * @return 影响行数 (如果为0说明库存不足扣减失败)
     */
    int decreaseStock(Long skuId, Integer count);
}