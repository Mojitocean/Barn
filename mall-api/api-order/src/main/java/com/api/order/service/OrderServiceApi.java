package com.api.order.service;

import com.api.order.dto.OrderCreateDTO;
import com.barn.core.domain.R;
/**
 * packageName com.api.order.service
 * 订单服务 Dubbo 接口定义
 * 供消费者（如 mall-web, mall-gateway）远程调用
 * @author mj
 * {@code @className} OrderServiceApi
 * {@code @date} 2025/11/22
 * {@code @description} TODO
 */
public interface OrderServiceApi {

    /**
     * 创建订单
     * @param createDTO 下单请求参数
     * @return 订单号 (OrderSn)
     */
    R<String> createOrder(OrderCreateDTO createDTO);
}