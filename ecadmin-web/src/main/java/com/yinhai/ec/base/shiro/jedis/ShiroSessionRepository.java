package com.yinhai.ec.base.shiro.jedis;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

/**
* @package com.yinhai.ec.base.shiro.jedis
* <p>Title: ShiroSessionRepository.java</p>
* <p>Description: 为了便于扩展，我引入了一个接口ShiroSessionRepository，可以用redis、mongoDB等进行实现。</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年3月2日 上午9:54:54
* @version 1.0
 */
public interface ShiroSessionRepository {
	void saveSession(Session session);  
	  
    void deleteSession(Serializable sessionId);  
  
    Session getSession(Serializable sessionId);  
  
    Collection<Session> getAllSessions();
}
