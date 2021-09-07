package com.cloud.sso.core.constant;

/**
 * @className: SsoConstant
 * @description: 常量值
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoConstant {
    /**
     * sso token, between browser and sso-server (web + token client)
     */
    public static final String SSO_TOKEN = "sso_token";


    /**
     * redirect url (web client)
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso user, request attribute (web client)
     */
    public static final String SSO_USER = "sso_user";


    /**
     * sso server address (web + token client)
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url, server relative path (web client)
     */
    public static final String SSO_LOGIN = "/login";
    /**
     * logout url, server relative path (web client)
     */
    public static final String SSO_LOGOUT = "/logout";


    /**
     * logout path, client relatice path
     */
    public static final String SSO_LOGOUT_PATH = "SSO_LOGOUT_PATH";

    /**
     * excluded paths, client relatice path, include path can be set by "filter-mapping"
     */
    public static final String SSO_EXCLUDED_PATHS = "SSO_EXCLUDED_PATHS";


    /**
     * login fail result
     */
    public static final ReturnResult<String> SSO_LOGIN_FAIL_RESULT = new ReturnResult(501, "sso not login.");
    /**
     * 设备类型 pc
     */
    public static final int USER_AGENT_TYPE_PC=1;

    /**
     * 设备类型 移动端
     */
    public static final int USER_AGENT_TYPE_MOBILE=0;



}
