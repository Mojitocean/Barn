package com.barn;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * packageName com.barn.order
 *
 * @author mj
 * @className ProductApplication
 * @date 2025/11/22
 * @description TODO
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.barn")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 商品服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}