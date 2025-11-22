package com.barn.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * packageName com.barn.api.product.dto
 * 库存扣减DTO
 * @author mj
 * @className StockDeductionDTO
 * @date 2025/11/22
 * @description TODO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDeductionDTO implements Serializable {
    /** SKU ID */
    private Long skuId;
    /** 扣减数量 */
    private Integer count;
}