package com.yinhai.ec.base.shiro.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.redis.RedisObjAdapter;

@SuppressWarnings("rawtypes")
public class JedisCache implements Cache {
	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public JedisCache(RedisObjAdapter cache) {
		logger = LoggerFactory.getLogger(getClass());
		keyPrefix = "shiro-session:";
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		} else {
			this.cache = cache;
			return;
		}
	}

	public JedisCache(RedisObjAdapter cache, String prefix) {
		this(cache);
		keyPrefix = prefix;
	}

	public Object get(Object key) throws CacheException {
		logger.debug((new StringBuilder()).append("\u6839\u636Ekey\u4ECERedis\u4E2D\u83B7\u53D6\u5BF9\u8C61 key [")
				.append(key).append("]").toString());
		if (key == null)
			return null;
		Object value;
		String jkey = getRedisSessionKey(key);
		value = cache.getObje(jkey);
		return value;
	}

	public Object put(Object key, Object value) throws CacheException {
		logger.debug((new StringBuilder()).append("\u6839\u636Ekey\u4ECE\u5B58\u50A8 key [").append(key).append("]")
				.toString());
		String jkey = getRedisSessionKey(key);
		cache.set(jkey, value);
		return value;
	}

	public Object remove(Object key) throws CacheException {
		logger.debug(
				(new StringBuilder()).append("\u4ECEredis\u4E2D\u5220\u9664 key [").append(key).append("]").toString());
		Object previous;
		previous = get(key);
		String jkey = getRedisSessionKey(key);
		cache.del(jkey);
		return previous;
	}

	public void clear() throws CacheException {
		logger.debug("\u4ECEredis\u4E2D\u5220\u9664\u6240\u6709\u5143\u7D20");
		try {
			Set<String> keys = cache.keys(keyPrefix + "*");
			for (String key : keys) {
				cache.del(key);
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public int size() {
		Set<String> keys = cache.keys(keyPrefix + "*");
		return keys.size();
	}

	@SuppressWarnings("unchecked")
	public Set keys() {
		Set<String> keys = cache.keys(keyPrefix + "*");
		if (CollectionUtils.isEmpty(keys))
			return Collections.emptySet();
		Set newKeys;
		newKeys = new HashSet();
		byte key[];
		for (Iterator i$ = keys.iterator(); i$.hasNext(); newKeys.add(key))
			key = (byte[]) i$.next();

		return newKeys;
	}

	@SuppressWarnings("unchecked")
	public Collection values() {
		List values;
		Set<String> keys = cache.keys(keyPrefix + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptyList();
		}
		values = new ArrayList(keys.size());
		Iterator i$ = keys.iterator();
		do {
			if (!i$.hasNext())
				break;
			String key = (String) i$.next();
			Object value = cache.getObje(key);
			if (value != null) {
				values.add(value);
			}
		} while (true);
		return Collections.unmodifiableList(values);
	}

	private String getRedisSessionKey(Object sessionId) {
		return this.keyPrefix + sessionId;
	}

	private Logger logger;
	private RedisObjAdapter cache;
	private String keyPrefix;

}
