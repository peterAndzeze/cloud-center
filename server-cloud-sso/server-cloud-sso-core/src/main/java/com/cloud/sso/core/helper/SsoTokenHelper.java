package com.cloud.sso.core.helper;

import com.cloud.cache.redis.util.RedisCacheUtil;
import com.cloud.framework.spring.util.SpringUtils;
import com.cloud.sso.core.dto.SsoUserDto;

/**
 * @className: SsoTokenHelper
 * @description: TOKEN 辅助类
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoTokenHelper {

    /**
     * make client token
     *TODO  临时方案，后期调整token生成规则
     *
     * @param ssoUserDto
     * @return
     */
    public static String makeSessionId(SsoUserDto ssoUserDto){
        return ssoUserDto.getUserId()+"_"+ssoUserDto.getVersion();
    }



    /**
     * 根据token 获取用户信息
     * @param token 用户token
     * @return 用户信息
     */
    public static SsoUserDto getSsoUserDtoByToken(String token){
        return SpringUtils.getBean(RedisCacheUtil.class).getCacheObject(token);
    }
}
