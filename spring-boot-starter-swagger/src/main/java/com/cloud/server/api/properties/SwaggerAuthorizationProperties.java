package com.cloud.server.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className: SwaggerAuthorizationProperties
 * @description: api权限类配置
 * @author: sw
 * @date: 2021/8/30
 **/
@Data
@ConfigurationProperties("swagger.authorization")
public class SwaggerAuthorizationProperties {

    /**
     * 鉴权策略ID，对应 SecurityReferences ID
     */
    private String name = "Authorization";

    /**
     * 鉴权策略，可选 ApiKey | BasicAuth | None，默认ApiKey
     */
    private String type = "ApiKey";

    /**
     * 鉴权传递的Header参数
     */
    private String keyName = "Access-Token";

    /**
     * 需要开启鉴权URL的正则
     */
    private String authRegex = "^.*$";
}

