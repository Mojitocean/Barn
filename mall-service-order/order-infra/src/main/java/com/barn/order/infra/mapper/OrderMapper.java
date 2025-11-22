package com.barn.order.infra.mapper;

import com.barn.order.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName com.barn.order.infra.mapper
 *
 * @author mj
 * @className OrderMapper
 * @date 2025/11/22
 * @description TODO
 */
@Mapper
public interface OrderMapper {
    // 插入并返回主键
    int insert(Order order);

    Order selectBySn(@Param("orderSn") String orderSn);
}