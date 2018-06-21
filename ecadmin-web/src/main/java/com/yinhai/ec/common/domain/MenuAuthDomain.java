package com.yinhai.ec.common.domain;

import java.io.Serializable;

public class MenuAuthDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer authId;

    private Integer roleId;

    private Integer menuId;

    private Integer roleCreate;

    private Integer roleQuery;

    private Integer roleUpdate;

    private Integer roleDelete;

    private Integer isParent;

    private String roleString;

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getRoleCreate() {
        return roleCreate;
    }

    public void setRoleCreate(Integer roleCreate) {
        this.roleCreate = roleCreate;
    }

    public Integer getRoleQuery() {
        return roleQuery;
    }

    public void setRoleQuery(Integer roleQuery) {
        this.roleQuery = roleQuery;
    }

    public Integer getRoleUpdate() {
        return roleUpdate;
    }

    public void setRoleUpdate(Integer roleUpdate) {
        this.roleUpdate = roleUpdate;
    }

    public Integer getRoleDelete() {
        return roleDelete;
    }

    public void setRoleDelete(Integer roleDelete) {
        this.roleDelete = roleDelete;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString == null ? null : roleString.trim();
    }
}