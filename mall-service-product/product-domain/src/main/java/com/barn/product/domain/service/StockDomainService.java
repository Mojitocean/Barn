package com.barn.product.domain.service;

import com.barn.core.exception.BizException;
import com.barn.product.domain.entity.Sku;
import com.barn.product.domain.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * packageName com.barn.order.domain.service
 * <p>
 * 【领域服务】库存服务
 * 处理跨实体的复杂逻辑，例如批量扣减库存
 *
 * @author mj
 * @className StockDomainService
 * @date 2025/11/22
 * @description TODO
 */
@Service
public class StockDomainService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 批量扣减库存逻辑
     * 这里演示了数据库乐观锁方案：update ... where stock >= count
     */
    public void deductStock(Map<Long, Integer> skuCountMap) {
        for (Map.Entry<Long, Integer> entry : skuCountMap.entrySet()) {
            Long skuId = entry.getKey();
            Integer count = entry.getValue();

            // 1. 先查出来做简单的校验 (快速失败)
            Sku sku = productRepository.findSkuById(skuId);
            if (sku == null) {
                throw new BizException("商品不存在: " + skuId);
            }
            // 利用实体内的业务逻辑检查
            sku.checkStock(count);

            // 2. 执行数据库原子扣减 (防止并发超卖的核心)
            int rows = productRepository.decreaseStock(skuId, count);
            if (rows == 0) {
                //log.warn("库存扣减失败，并发冲突或库存不足: skuId={}", skuId);
                throw new BizException("商品[" + sku.getSkuName() + "]库存不足");
            }
        }
    }
}