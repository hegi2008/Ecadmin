package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class OnlineDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

    private String sessionid;

    private Date loginTime;

    private String loginIp;

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

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }
}