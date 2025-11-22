package com.barn.api.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * packageName com.barn.api.product.dto
 * 商品传输对象
 * @author mj
 * @className SkuInfoDTO
 * @date 2025/11/22
 * @description TODO
 */
@Data
public class SkuInfoDTO implements Serializable {
    private Long id;
    private Long spuId;
    private String skuName;
    private BigDecimal price;
    private Integer stock;
    private String specJson;
}