package com.cloud.framework;

import com.cloud.framework.spring.util.SpringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: FrameworkConfiguration
 * @description: 框架工具类启动
 * @author: sw
 * @date: 2021/9/7
 **/
@Configuration
public class FrameworkConfiguration {
    /**
     * 初始化spring 工具类
     * @return
     */
    @Bean
    public SpringUtils springUtils(){
        return new SpringUtils();
    }
}
