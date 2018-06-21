package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class OnlineLogDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer logId;

    private Integer userId;

    private String sessionid;

    private String loginIp;

    private Date loginTime;

    private Date logoutTime;
    
    private Integer isForceLogout;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid == null ? null : sessionid.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

	public Integer getIsForceLogout() {
		return isForceLogout == null ? 0:isForceLogout;
	}

	public void setIsForceLogout(Integer isForceLogout) {
		this.isForceLogout = isForceLogout;
	}
}