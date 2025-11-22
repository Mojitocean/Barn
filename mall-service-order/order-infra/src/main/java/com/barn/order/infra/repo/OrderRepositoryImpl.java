package com.barn.order.infra.repo;

import com.barn.order.domain.entity.Order;
import com.barn.order.domain.entity.OrderItem;
import com.barn.order.domain.repo.OrderRepository;
import com.barn.order.infra.mapper.OrderItemMapper;
import com.barn.order.infra.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName com.barn.order.infra.repo
 * <p>
 * 【仓储实现】
 * 将领域对象 (Entity) 拆解并保存到数据库 (Data Model)
 *
 * @author mj
 * @className OrderRepositoryImpl
 * @date 2025/11/22
 * @description TODO
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Order order) {
        // 1. 保存主表
        // 注意：这里通常需要将 Domain Entity 转换为 PO (Persistent Object)
        // 为了代码简洁，假设 Mapper 直接支持 Entity 映射 (MyBatis 强大之处)
        orderMapper.insert(order);

        // 2. 保存子表
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                // 将刚生成的主订单ID回填给子单
                // item.setOrderId(order.getId()); // 需要在 Entity 加 Setter 或通过构造器
                orderItemMapper.insert(item, order.getId());
            }
        }
    }

    @Override
    public Order findBySn(String orderSn) {
        return orderMapper.selectBySn(orderSn);
    }
}