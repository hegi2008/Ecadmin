package com.yinhai.ec.base.session.dao;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

public interface HYBaseSessionDao {
	public abstract void setRepeatLogin(Boolean repeatLogin);
	public abstract Boolean getRepeatLogin();
	public abstract Collection<Session> getActiveSessions();
	public abstract Session readSession(Serializable sessionId);
}
