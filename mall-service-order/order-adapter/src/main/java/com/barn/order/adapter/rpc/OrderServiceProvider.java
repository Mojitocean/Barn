package com.barn.order.adapter.rpc;

import com.api.barn.order.dto.OrderCreateDTO;
import com.api.barn.order.service.OrderServiceApi;
import com.barn.core.domain.R;
import com.barn.order.app.service.OrderAppService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * packageName com.barn.order.adapter.rpc
 * 【适配层】Dubbo RPC 适配
 * 将 Application Service 暴露为 Dubbo 服务
 *
 * @author mj
 * @className OrderServiceProvider
 * @date 2025/11/22
 * @description TODO
 */

@DubboService
public class OrderServiceProvider implements OrderServiceApi {

    @Autowired
    private OrderAppService orderAppService;

    @Override
    public R<String> createOrder(OrderCreateDTO createDTO) {
        try {
            String orderSn = orderAppService.createOrder(createDTO);
            return R.ok(orderSn);
        } catch (Exception e) {
            // 全局异常处理兜底
            return R.fail(e.getMessage());
        }
    }
}