package com.barn.product.infra.repo;

import com.barn.product.domain.entity.Sku;
import com.barn.product.domain.repo.ProductRepository;
import com.barn.product.infra.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * packageName com.barn.product.infra.repo
 * 商品仓储实现
 *
 * @author mj
 * @className ProductRepositoryImpl
 * @date 2025/11/22
 * @description TODO
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public Sku findSkuById(Long id) {
        return skuMapper.selectById(id);
    }

    @Override
    public int decreaseStock(Long skuId, Integer count) {
        return skuMapper.decreaseStock(skuId, count);
    }
}