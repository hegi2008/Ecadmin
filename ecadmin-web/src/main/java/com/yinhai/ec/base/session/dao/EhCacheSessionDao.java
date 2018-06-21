package com.yinhai.ec.base.session.dao;  

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

/**
* @package com.yinhai.ec.base.session.dao
* <p>Title: BaseCacheSessionDao.java</p>
* <p>Description: 自定义实现SessionDAO</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-8 上午9:31:06
* @version 1.0
 */
public class EhCacheSessionDao extends CachingSessionDAO implements HYBaseSessionDao{

	/**
	 * 默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。</p>
	 */
	public EhCacheSessionDao(){
		setCacheManager(new AbstractCacheManager() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			protected Cache createCache(String name) throws CacheException {
				return new MapCache(name, new ConcurrentHashMap());
			}
		});
	}
	
	/**
	 * 删除session
	 */
	@Override
	protected void doDelete(Session session) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 修改session
	 */
	@Override
	protected void doUpdate(Session session) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 创建session
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		return sessionId;
	}

	/**
	 *读取session 
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		return null;
	}

	private Boolean repeatLogin = true;

	@Override
	public void setRepeatLogin(Boolean repeatLogin) {
		this.repeatLogin = repeatLogin;
	}

	@Override
	public Boolean getRepeatLogin() {
		return this.repeatLogin;
	}
}
 