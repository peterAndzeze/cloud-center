package com.cloud.sso.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @className: SsoConfigProperties
 * @description: 配置类
 * @author: sw
 * @date: 2021/9/7
 **/
@ConfigurationProperties("sso")
public class SsoConfigProperties implements Serializable {


    private static final long serialVersionUID = -3153835459179477946L;
    /**
     * Ticket有效期 (单位: 毫秒)
     */
    public long ticketTimeout = 18000;

    /**
     * 所有允许的授权回调地址，多个用逗号隔开 (不在此列表中的URL将禁止下放ticket)
     */
    public String allowUrl = "*";

    /**
     * 接口调用秘钥 (用于SSO模式三单点注销的接口通信身份校验)
     */
    public String secretkey;

    /**
     * SSO-Server端 单点登录地址
     */
    public String ssoServer;

    /**
     * SSO-Server端 Ticket校验地址
     */
    public String checkTicketUrl;

    /**
     * SSO-Server端 单点注销地址
     */
    public String logoutPath;

    /**
     * SSO-Client端 当前Client端的单点注销回调URL （为空时自动获取）
     */
    public String logoutCall;
    /**
     * 过滤路径
     */
    private String excludedPaths;

    /**
     * SSO-Server端 账号资料查询地址
     */
    public String userinfoUrl;
    /**
     * 服务实例类型 默认是server 客户端是client
     */
    private String serverType="server";

    public long getTicketTimeout() {
        return ticketTimeout;
    }

    public void setTicketTimeout(long ticketTimeout) {
        this.ticketTimeout = ticketTimeout;
    }

    public String getAllowUrl() {
        return allowUrl;
    }

    public void setAllowUrl(String allowUrl) {
        this.allowUrl = allowUrl;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }



    public String getCheckTicketUrl() {
        return checkTicketUrl;
    }

    public void setCheckTicketUrl(String checkTicketUrl) {
        this.checkTicketUrl = checkTicketUrl;
    }

    public String getSsoServer() {
        return ssoServer;
    }

    public void setSsoServer(String ssoServer) {
        this.ssoServer = ssoServer;
    }

    public String getLogoutPath() {
        return logoutPath;
    }

    public void setLogoutPath(String logoutPath) {
        this.logoutPath = logoutPath;
    }

    public String getLogoutCall() {
        return logoutCall;
    }

    public void setLogoutCall(String logoutCall) {
        this.logoutCall = logoutCall;
    }

    public String getUserinfoUrl() {
        return userinfoUrl;
    }

    public void setUserinfoUrl(String userinfoUrl) {
        this.userinfoUrl = userinfoUrl;
    }

    public String getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(String excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
}
