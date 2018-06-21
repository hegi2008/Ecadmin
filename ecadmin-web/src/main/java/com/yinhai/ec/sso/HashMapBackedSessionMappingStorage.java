package com.yinhai.ec.sso;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
* @package com.yinhai.ec.sso
* <p>Title: HashMapBackedSessionMappingStorage.java</p>
* <p>Description: 重写SessionMappingStorage,以实现使用shiro自管理session的单点登出</p>
* @author cjh
* @date 2016年9月8日 上午9:28:00
* @version 1.0
 */
public final class HashMapBackedSessionMappingStorage {
	
    /**
     * Maps the ID from the CAS server to the Session ID.
     */
    private final Map<String,Serializable> MANAGED_SESSIONS_ID = new HashMap<String,Serializable>();

	public synchronized void addSessionById(String mappingId, Session session) {
        MANAGED_SESSIONS_ID.put(mappingId, session.getId());

	}                               

	public synchronized Serializable getSessionIDByMappingId(String mappingId) {
        return MANAGED_SESSIONS_ID.get(mappingId);
	}
}
