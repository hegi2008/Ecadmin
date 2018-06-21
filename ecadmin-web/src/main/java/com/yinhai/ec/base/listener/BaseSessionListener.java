package com.yinhai.ec.base.listener;  

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yinhai.ec.base.service.online.OnlineService;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.OnlineLogDomain;
import com.yinhai.ec.common.domain.UserDomain;
  
/**
* @package com.yinhai.ec.base.listener
* <p>Title: BaseSessionListener.java</p>
* <p>Description: 自己管理的会话监听器</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-6 下午2:17:16
* @version 1.0
 */
public class BaseSessionListener implements SessionListener{
	@Autowired
	private OnlineService onlineService;
	
	private static final Logger log = LoggerFactory.getLogger(com.yinhai.ec.base.listener.BaseSessionListener.class);
	
	/**
	 * 会话过期时触发
	 */
	@Override
	public void onExpiration(Session session) {
		exeOnline(session);
		exeOnlineLog(session);
	}

	/**
	 * 会话创建时触发 先于Realm执行,此时的session是空的 只有ID 没有用户信息
	 */
	@Override
	public void onStart(Session session) {
		
	}

	/**
	 * 会话停止/退出时触发
	 */
	@Override
	public void onStop(Session session) {
		exeOnline(session);
		exeOnlineLog(session);
	}
	
	/**
	  * @package com.yinhai.ec.base.listener
	  * @method exeOnlineLog 方法 
	  * @describe <p>方法说明:更新用户登陆历史记录</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	private void exeOnlineLog(Session session) {
		if(session.getId() != null && session.getAttribute(HYConst.SESSION_USER) != null){
			UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			OnlineLogDomain logDomain = new OnlineLogDomain();
			logDomain.setUserId(user.getUserId());
			logDomain.setSessionid(session.getId().toString());
			if (session.getAttribute(HYConst.SESSION_FORCE_LOGOUT_KEY) != null) {
				logDomain.setIsForceLogout(HYConst.STATUS_YES);
			}
			try {
				onlineService.updateOnlineLogout(logDomain);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("用户:["+user.getUserName()+"]登出,修改登录历史记录的登出时间时出错", e);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("用户:["+user.getUserName()+"]登出,已修改登录历史记录的登出时间");
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.listener
	  * @method exeOnlineLog 方法 
	  * @describe <p>方法说明:更新用户登陆历史记录</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	private void exeOnline(Session session) {
		/*if(session.getId() != null && session.getAttribute(HYConst.SESSION_USER) != null){
			UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			OnlineDomain domain = new OnlineDomain();
			domain.setUserId(user.getUserId());
			domain.setSessionid(session.getId().toString());
			try {
				onlineService.deleteOnlineByUserIdAndSessionid(domain);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("用户:["+user.getUserName()+"]登出,删除在线用户时出错", e);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("用户:["+user.getUserName()+"]登出,已删除在线用户");
			}
		}*/
	}
}
 