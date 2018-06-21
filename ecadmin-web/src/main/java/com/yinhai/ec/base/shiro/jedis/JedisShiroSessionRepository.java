package com.yinhai.ec.base.shiro.jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.redis.RedisObjAdapter;

public class JedisShiroSessionRepository implements ShiroSessionRepository{
	private RedisObjAdapter redisObjAdapter;
    private String REDIS_SHIRO_SESSION_HEADER = "shiro-session:";
    private static final Logger logger = LoggerFactory.getLogger(JedisShiroSessionRepository.class);
	
	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {  
			if(logger.isErrorEnabled()){
				logger.error("session或者session id为空");
			}
            return;  
        }
		String key = getRedisSessionKey(session.getId());
		Long timeOut = session.getTimeout()/1000;
		try {
			redisObjAdapter.set(key, session, timeOut);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("保存session失败:"+e);
			}
		}
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		if(sessionId == null){
			if(logger.isErrorEnabled()){
				logger.equals("sessionid为空,不能删除");
			}
		}
		try {
			String key = getRedisSessionKey(sessionId);
			redisObjAdapter.del(key);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.equals("删除session失败="+sessionId+":"+e);
			}
		}
	}

	@Override
	public Session getSession(Serializable sessionId) {
		if(sessionId == null){
			if(logger.isErrorEnabled()){
				logger.equals("sessionid为空");
			}
		}
		Object session = null;
		try {
			String key = getRedisSessionKey(sessionId);
			session = redisObjAdapter.getObje(key);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.equals("获取id为:"+sessionId+"的session失败");
			}
		}
		return (Session) (session != null && session instanceof Session ?session:null);
	}

	@Override
	public Collection<Session> getAllSessions() {
		Set<Session> sessions = new HashSet<Session>();
		try {
			Set<String> keys = redisObjAdapter.keys(this.REDIS_SHIRO_SESSION_HEADER+"*");
			for (String string : keys) {
				Object object = redisObjAdapter.getObje(string);
				if(object != null && object instanceof Session){
					sessions.add((Session)object);
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取所有session失败"+e);
			}
		}
		return sessions;
	}
	
	/**
	  * @package com.yinhai.ec.base.shiro.jedis
	  * @method getRedisSessionKey 方法 
	  * @describe <p>方法说明:获取redis中的session key</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年3月2日 上午10:04:45
	 */
    private String getRedisSessionKey(Serializable sessionId) {  
        return this.REDIS_SHIRO_SESSION_HEADER + sessionId;  
    }

	public RedisObjAdapter getRedisObjAdapter() {
		return redisObjAdapter;
	}

	public void setRedisObjAdapter(RedisObjAdapter redisObjAdapter) {
		this.redisObjAdapter = redisObjAdapter;
	}

	public String getREDIS_SHIRO_SESSION_HEADER() {
		return REDIS_SHIRO_SESSION_HEADER;
	}

	public void setREDIS_SHIRO_SESSION_HEADER(String rEDIS_SHIRO_SESSION_HEADER) {
		REDIS_SHIRO_SESSION_HEADER = rEDIS_SHIRO_SESSION_HEADER;
	}  


}
