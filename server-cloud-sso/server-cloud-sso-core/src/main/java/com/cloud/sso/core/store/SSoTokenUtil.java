package com.cloud.sso.core.store;

import com.cloud.sso.core.constant.SsoConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @className: TokenUtil
 * @description: token 工具类
 * @author: sw
 * @date: 2021/9/7
 **/
public class SSoTokenUtil {
    /**
     * 从请求中获取token
     * @param req
     * @return
     */
    public static String getTokenFromReq(HttpServletRequest req) {
        return req.getParameter(SsoConstant.SSO_TOKEN);
    }

}
