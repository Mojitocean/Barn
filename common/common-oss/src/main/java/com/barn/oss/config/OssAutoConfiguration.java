package com.barn.oss.config;

import com.barn.oss.service.OssTemplate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * packageName com.barn.core.config
 *
 * @author mj
 * @className OssAutoConfiguration
 * @date 2025/5/26
 * @description TODO
 */
@AllArgsConstructor
@EnableConfigurationProperties({OssConfig.class})
public class OssAutoConfiguration {
    private final OssConfig config;

    @Bean
    @Primary
    @ConditionalOnMissingBean(OssTemplate.class)
    @ConditionalOnProperty(name = "oss.enable", havingValue = "true")
    public OssTemplate ossTemplate() {
        return new OssTemplate(config);
    }
}