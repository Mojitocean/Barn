package com.barn.order.infra.mapper;

import com.barn.order.domain.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName com.barn.order.infra.mapper
 * 订单明细 Mapper
 *
 * @author mj
 * @className OrderItemMapper
 * @date 2025/11/22
 * @description TODO
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 插入单条订单明细
     * 注意：OrderItem 实体本身可能不包含 orderId (它是依附于 Aggregate 的)，
     * 所以这里通常需要显式传入 orderId，或者在 Service 层先设置好 Entity 的 orderId。
     * 在本架构中，我们在 XML 中处理这个逻辑。
     */
    int insert(@Param("item") OrderItem item, @Param("orderId") Long orderId);

    /**
     * 批量插入 (性能优化项，可选)
     */
    int insertBatch(@Param("items") List<OrderItem> items, @Param("orderId") Long orderId);

    /**
     * 根据订单ID查询明细列表
     */
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);
}