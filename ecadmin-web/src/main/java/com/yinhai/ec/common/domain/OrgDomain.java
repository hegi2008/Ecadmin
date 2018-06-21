package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrgDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer orgId;

    private Integer parentOrgId;

    private String orgName;

    private String costomno;

    private Integer orgType;

    private String orgIdPath;

    private String orgNamePath;

    private Integer orgLevel;

    private Integer sort;

    private Integer userCreater;

    private Date userCreateTime;

    private Integer orgStatus;

    private Integer isLeaf;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Integer parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCostomno() {
        return costomno;
    }

    public void setCostomno(String costomno) {
        this.costomno = costomno == null ? null : costomno.trim();
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOrgIdPath() {
        return orgIdPath;
    }

    public void setOrgIdPath(String orgIdPath) {
        this.orgIdPath = orgIdPath == null ? null : orgIdPath.trim();
    }

    public String getOrgNamePath() {
        return orgNamePath;
    }

    public void setOrgNamePath(String orgNamePath) {
        this.orgNamePath = orgNamePath == null ? null : orgNamePath.trim();
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getUserCreater() {
        return userCreater;
    }

    public void setUserCreater(Integer userCreater) {
        this.userCreater = userCreater;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Integer getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(Integer orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",this.orgId);
        map.put("pId",this.parentOrgId);
        map.put("name",this.orgName);
        return map;
    }
}