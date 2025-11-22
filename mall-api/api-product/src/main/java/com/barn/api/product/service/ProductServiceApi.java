package com.barn.api.product.service;

import com.barn.api.product.dto.SkuInfoDTO;
import com.barn.api.product.dto.StockDeductionDTO;
import com.barn.core.domain.R;

import java.util.List;

/**
 * packageName com.barn.api.product.service
 * 商品服务RPC接口
 * @author mj
 * @className ProductServiceApi
 * @date 2025/11/22
 * @description TODO
 */
public interface ProductServiceApi {

    /**
     * 根据 SKU ID 获取商品详情 (用于下单前确认价格和库存)
     */
    R<SkuInfoDTO> getSkuInfo(Long skuId);

    /**
     * 锁定/扣减库存
     * 这是一个事务性操作
     */
    R<Boolean> deductStock(List<StockDeductionDTO> deductionList);
}