package com.yinhai.ec.base.shiro.jedis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.redis.RedisObjAdapter;

/**
* @package com.yinhai.ec.base.shiro.jedis
* <p>Title: JedisCacheManager.java</p>
* <p>Description: 针对shiro的redis缓存管理器</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 贵州医美达</p>
* @author cjh
* @date 2016年3月2日 下午4:22:32
* @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JedisCacheManager implements CacheManager ,Initializable, Destroyable{

	private String REDIS_SHIRO_SESSION_HEADER = null;
	private static final Logger logger = LoggerFactory.getLogger(JedisCacheManager.class);
	private final ConcurrentMap caches = new ConcurrentHashMap();
	private RedisObjAdapter redisObjAdapter;
	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug((new StringBuilder()).append("\u83B7\u53D6\u540D\u79F0\u4E3A: ").append(name)
				.append(" \u7684RedisCache\u5B9E\u4F8B").toString());
		Cache c = (Cache) caches.get(name);
		if (c == null) {
			if(getREDIS_SHIRO_SESSION_HEADER() == null || "".equals(getREDIS_SHIRO_SESSION_HEADER())){
				this.REDIS_SHIRO_SESSION_HEADER = "shiro-session:";
			}
			c = new JedisCache(redisObjAdapter, this.REDIS_SHIRO_SESSION_HEADER);
			caches.put(name, c);
		}
		return c;
	}

	public RedisObjAdapter getRedisObjAdapter() {
		return redisObjAdapter;
	}

	public void setRedisObjAdapter(RedisObjAdapter redisObjAdapter) {
		this.redisObjAdapter = redisObjAdapter;
	}

	@Override
	public void destroy() throws Exception {
		
	}

	@Override
	public void init() throws ShiroException {
		
	}

	public String getREDIS_SHIRO_SESSION_HEADER() {
		return REDIS_SHIRO_SESSION_HEADER;
	}

	public void setREDIS_SHIRO_SESSION_HEADER(String rEDIS_SHIRO_SESSION_HEADER) {
		REDIS_SHIRO_SESSION_HEADER = rEDIS_SHIRO_SESSION_HEADER;
	}


}
