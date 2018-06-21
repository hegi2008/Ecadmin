package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class MenuDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String menuUrl;

    private String menuIcon;

    private Integer menuStatus;

    private Integer menuCreater;

    private Date menuCreateTime;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(Integer menuStatus) {
        this.menuStatus = menuStatus;
    }

    public Integer getMenuCreater() {
        return menuCreater;
    }

    public void setMenuCreater(Integer menuCreater) {
        this.menuCreater = menuCreater;
    }

    public Date getMenuCreateTime() {
        return menuCreateTime;
    }

    public void setMenuCreateTime(Date menuCreateTime) {
        this.menuCreateTime = menuCreateTime;
    }
}