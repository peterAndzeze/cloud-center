package com.cloud.server.api.configuration;

import com.cloud.server.api.properties.SwaggerUiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @className: SwaggerUiConfiguration
 * @description: ui配置类
 * @author: sw
 * @date: 2021/8/30
 **/
@Configuration
@EnableConfigurationProperties(SwaggerUiProperties.class)
public class SwaggerUiConfiguration {
    @Bean
    public UiConfiguration uiConfiguration(SwaggerUiProperties swaggerUiProperties) {
        return UiConfigurationBuilder.builder()
                .deepLinking(swaggerUiProperties.getDeepLinking())
                .defaultModelExpandDepth(swaggerUiProperties.getDefaultModelExpandDepth())
                .defaultModelRendering(swaggerUiProperties.getDefaultModelRendering())
                .defaultModelsExpandDepth(swaggerUiProperties.getDefaultModelsExpandDepth())
                .displayOperationId(swaggerUiProperties.getDisplayOperationId())
                .displayRequestDuration(swaggerUiProperties.getDisplayRequestDuration())
                .docExpansion(swaggerUiProperties.getDocExpansion())
                .maxDisplayedTags(swaggerUiProperties.getMaxDisplayedTags())
                .operationsSorter(swaggerUiProperties.getOperationsSorter())
                .showExtensions(swaggerUiProperties.getShowExtensions())
                .tagsSorter(swaggerUiProperties.getTagsSorter())
                .validatorUrl(swaggerUiProperties.getValidatorUrl())
                .build();
    }
}
