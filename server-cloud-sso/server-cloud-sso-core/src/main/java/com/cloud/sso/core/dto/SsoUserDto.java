package com.cloud.sso.core.dto;

import java.io.Serializable;

/**
 * @className: SsoUserDto
 * @description: 登陆用户信息
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoUserDto implements Serializable {
    private static final long serialVersionUID = -3683564800041537339L;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 版本
     */
    private String version;
    /**
     * token刷新完成时间
     */
    private long expireFreshTime;

    /**
     * 用户角色信息
     */
    private SsoUserRoleDto ssoUserRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SsoUserRoleDto getSsoUserRole() {
        return ssoUserRole;
    }

    public void setSsoUserRole(SsoUserRoleDto ssoUserRole) {
        this.ssoUserRole = ssoUserRole;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }
}
