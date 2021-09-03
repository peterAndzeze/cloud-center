package com.cloud.server.api;

import com.cloud.server.api.configuration.DocketConfiguration;
import com.cloud.server.api.configuration.SwaggerAuthorizationConfiguration;
import com.cloud.server.api.configuration.SwaggerUiConfiguration;
import com.cloud.server.api.factory.DocketBeanFactoryPostProcessor;
import com.cloud.server.api.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * @className: SwaggerAutoConfiguration
 * @description: Swagger 自动注册类
 * @author: sw
 * @date: 2021/8/30
 **/
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
@Import({SwaggerUiConfiguration.class, SwaggerAuthorizationConfiguration.class, DocketConfiguration.class})
public class SwaggerAutoConfiguration {
    @Bean
    public DocketBeanFactoryPostProcessor docketBeanFactoryPostProcessor() {
        return new DocketBeanFactoryPostProcessor();
    }
}
