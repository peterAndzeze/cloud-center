package com.cloud.sso.core.store;

import com.cloud.cache.redis.util.RedisCacheUtil;
import com.cloud.framework.spring.util.SpringUtils;
import com.cloud.sso.core.config.SsoConfigProperties;
import com.cloud.sso.core.dto.SsoUserDto;

import java.util.concurrent.TimeUnit;

/**
 * @className: SsoTokenStore
 * @description: Sso Token 存储
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoTokenStore {

    /**
     * 放入token
     * @param token 用户token
     * @param ssoUserDto 用户信息
     */
    public static void put(String token, SsoUserDto ssoUserDto) {
        SsoConfigProperties ssoConfigProperties = SpringUtils.getBean(SsoConfigProperties.class);
        long ticketTimeout = ssoConfigProperties.getTicketTimeout();
        SpringUtils.getBean(RedisCacheUtil.class).setCacheObject(token,ssoUserDto, (int) ticketTimeout, TimeUnit.MILLISECONDS);

    }

    /**
     * 放入token
     * @param token 用户token
     */
    public static void remove(String token) {

        SpringUtils.getBean(RedisCacheUtil.class).deleteObject(token);

    }
}
