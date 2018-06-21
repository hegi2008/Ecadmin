package com.yinhai.ec.sso;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;

import com.yinhai.ec.base.session.dao.HYBaseSessionDao;

public class ShiroCasSessionDao implements HYBaseSessionDao{

	public ShiroCasSessionDao() {
		this.repeatLogin = false;
	}
	
	@Override
	public void setRepeatLogin(Boolean repeatLogin) {
		this.repeatLogin = repeatLogin;
	}

	@Override
	public Boolean getRepeatLogin() {
		return this.repeatLogin;
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return Collections.emptySet();
	}
	@Override
	public Session readSession(final Serializable sessionId) {
		return SecurityUtils.getSecurityManager().getSession(new SessionKey() {
			@Override
			public Serializable getSessionId() {
				return sessionId;
			}
		});
	}
	private Boolean repeatLogin = true;
}
