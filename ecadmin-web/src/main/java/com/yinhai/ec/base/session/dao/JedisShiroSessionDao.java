package com.yinhai.ec.base.session.dao;

import java.io.Serializable;
import java.util.Collection;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.shiro.jedis.ShiroSessionRepository;

public class JedisShiroSessionDao extends AbstractSessionDAO implements HYBaseSessionDao{

	private ShiroSessionRepository sessionRepository;
	private Boolean repeatLogin = true;
	private static final Logger logger = LoggerFactory.getLogger(JedisShiroSessionDao.class);
	
	@Override
	public void delete(Session session) {
		if(session == null){
			logger.error("session is null,when delete session");
		}
		if(session.getId() != null){
			getSessionRepository().deleteSession(session.getId());
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return getSessionRepository().getAllSessions();
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		getSessionRepository().saveSession(session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionid = generateSessionId(session);
		assignSessionId(session, sessionid);
		getSessionRepository().saveSession(session);
		return sessionid;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return getSessionRepository().getSession(sessionId);
	}
	
	@Override
	public void setRepeatLogin(Boolean repeatLogin) {
		this.repeatLogin = repeatLogin;
	}

	@Override
	public Boolean getRepeatLogin() {
		return this.repeatLogin;
	}

	public ShiroSessionRepository getSessionRepository() {
		return sessionRepository;
	}

	public void setSessionRepository(ShiroSessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
}
