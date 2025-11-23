package com.barn;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * packageName com.barn.order
 *
 * @author mj
 * @className OrderApplication
 * @date 2025/11/22
 * @description TODO
 */

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.barn")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 订单服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}