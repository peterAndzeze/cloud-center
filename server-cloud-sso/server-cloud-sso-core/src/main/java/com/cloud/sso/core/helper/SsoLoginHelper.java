package com.cloud.sso.core.helper;

import com.cloud.cache.redis.util.RedisCacheUtil;
import com.cloud.framework.spring.util.SpringUtils;
import com.cloud.sso.core.config.SsoConfigProperties;
import com.cloud.sso.core.dto.SsoUserDto;
import com.cloud.sso.core.store.SSoTokenUtil;
import com.cloud.sso.core.store.SsoTokenStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: SsoLoginHelper
 * @description: 用户登陆辅助
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoLoginHelper {
    /**
     * 用户登陆信息
     *
     * @param response
     * @param ssoUserDto
     * @param ifRemember
     * @param resourceType 来源
     */
    public static void login(HttpServletRequest request,HttpServletResponse response,
                             SsoUserDto ssoUserDto,
                             boolean ifRemember, int resourceType) {

        String token = SSoTokenUtil.getTokenFromReq(request);
        if (token == null) {
            throw new RuntimeException("parseStoreKey Fail, token:" + token);
        }
        SsoTokenStore.put(token, ssoUserDto);

    }

    public static void logout(HttpServletRequest request) {
        String token = SSoTokenUtil.getTokenFromReq(request);
        SsoTokenStore.remove(token);
    }

    /**
     * 检查是否登陆
     * @param req 请求
     * @return
     */
    public static SsoUserDto loginCheck(HttpServletRequest req) {
        String token = SSoTokenUtil.getTokenFromReq(req);
        SsoUserDto ssoUserDto = SpringUtils.getBean(RedisCacheUtil.class).getCacheObject(token);
        if (null != ssoUserDto) {
            SsoConfigProperties ssoConfigProperties = SpringUtils.getBean(SsoConfigProperties.class);

            // After the expiration time has passed half, Auto refresh
            if ((System.currentTimeMillis() - ssoUserDto.getExpireFreshTime())<ssoConfigProperties.getTicketTimeout()) {
                ssoUserDto.setExpireFreshTime(System.currentTimeMillis());
                SsoTokenStore.put(token, ssoUserDto);
            }
        }
        return ssoUserDto;
    }
}
