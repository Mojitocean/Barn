package com.barn.order.adapter.web;

import com.api.order.dto.OrderCreateDTO;
import com.barn.core.domain.R;
import com.barn.order.app.service.OrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName com.barn.order.adapter.web
 * 【适配层】Web 接口适配
 * 负责接收 HTTP 请求，解析参数，调用 App 层
 *
 * @author mj
 * @className OrderController
 * @date 2025/11/22
 * @description TODO
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderAppService orderAppService;

    @PostMapping("/create")
    public R<String> create(@RequestBody OrderCreateDTO createDTO) {
        // 这里可以做简单的参数格式校验 (@Valid)
        // 委派给应用层
        String orderSn = orderAppService.createOrder(createDTO);
        return R.ok(orderSn);
    }
}