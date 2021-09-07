package com.cloud.sso.core;

import com.cloud.sso.core.config.SsoConfigProperties;
import com.cloud.sso.core.constant.SsoConstant;
import com.cloud.sso.core.filter.SsoCoreFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @className: SsoCoreConfiguration
 * @description: 逻辑类封装
 * @author: sw
 * @date: 2021/9/7
 **/
@Configuration
@EnableConfigurationProperties(SsoConfigProperties.class)
public class SsoCoreConfiguration {
    @Autowired
    private  SsoConfigProperties ssoConfigProperties;
    @ConditionalOnProperty(value ="sso.serverType", havingValue = "client", matchIfMissing = false)
    @Bean
    public FilterRegistrationBean ssoFilterRegistration() {
        System.out.println("我初始化了*****************");
        //  filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("ssoCoreFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoCoreFilter());
        registration.addInitParameter(SsoConstant.SSO_SERVER, ssoConfigProperties.getSsoServer());
        registration.addInitParameter(SsoConstant.SSO_LOGOUT_PATH, ssoConfigProperties.getLogoutPath());
        registration.addInitParameter(SsoConstant.SSO_EXCLUDED_PATHS, ssoConfigProperties.getExcludedPaths());

        return registration;
    }
}
