package com.barn.order.domain.repo;

import com.barn.order.domain.entity.Order;

/**
 * packageName com.barn.order.domain.repo
 * 【仓储接口】
 * 只要定义接口，不需要在 Domain 层实现。
 * 实现类在 Infra 层。
 *
 * @author mj
 * @className OrderRepository
 * @date 2025/11/22
 * @description TODO
 */
public interface OrderRepository {

    /**
     * 保存订单 (聚合根)
     * 实现时需同时保存 Order 和 List<OrderItem>
     */
    void save(Order order);

    /**
     * 根据订单号查询
     */
    Order findBySn(String orderSn);
}