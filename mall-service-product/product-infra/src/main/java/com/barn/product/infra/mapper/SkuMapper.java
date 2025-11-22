package com.barn.product.infra.mapper;

import com.barn.product.domain.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName com.barn.product.infra.mapper
 *
 * @author mj
 * @className SkuMapper
 * @date 2025/11/22
 * @description TODO
 */
@Mapper
public interface SkuMapper {

    Sku selectById(@Param("id") Long id);

    /**
     * 乐观锁扣减库存
     * UPDATE pms_sku SET stock = stock - #{count} WHERE id = #{id} AND stock >= #{count}
     */
    int decreaseStock(@Param("id") Long id, @Param("count") Integer count);
}