package com.cloud.sso.core.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * @className: SsoUserRole
 * @description: 用户角色信息
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoUserRoleDto implements Serializable {
    private static final long serialVersionUID = -5325298698759679473L;
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 权限集合
     */
    private Set<String> permissionList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<String> permissionList) {
        this.permissionList = permissionList;
    }
}
