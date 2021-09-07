package com.cloud.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: SsoServerApplication
 * @description: ssoServer 启动
 * @author: sw
 * @date: 2021/9/7
 **/
@SpringBootApplication
public class SsoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class,args);
    }
}
