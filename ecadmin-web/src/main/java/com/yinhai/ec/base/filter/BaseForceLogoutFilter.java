package com.yinhai.ec.base.filter;  

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.util.HYConst;
  
/**
* @package com.yinhai.ec.base.filter
* <p>Title: BaseForceLogoutFilter.java</p>
* <p>Description: 强制退出拦截器</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-7 下午3:32:38
* @version 1.0
 */
public class BaseForceLogoutFilter extends AccessControlFilter{
	private static final Logger logger = LoggerFactory.getLogger(com.yinhai.ec.base.filter.BaseForceLogoutFilter.class);
	private static Boolean isRepeat = false;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object subject) throws Exception {
		Session session = getSubject(request, response).getSession(false);
		if (session == null) {
			return true;
		}
		// 先判断重复登录 再判断强制退出
		if (session.getAttribute(HYConst.SESSION_REPEAT_LOGIN_KEY) != null) {
			isRepeat = true;
			return false;
		}
		return session.getAttribute(HYConst.SESSION_FORCE_LOGOUT_KEY) == null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		Session session = getSubject(request, response).getSession(false);
		getSubject(request, response).logout();
		String loginUrl = null;
		if (isRepeat) {
			loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "repeatLogin=true";
		}else{
			loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=true";
		}
		WebUtils.issueRedirect(request, response, loginUrl);
		if (logger.isDebugEnabled()) {
			if (isRepeat) {
				logger.debug("获取到重复登录标志,sessionid="+session.getId()+",已强制下线");
			}else{
				logger.debug("获取到强制退出标志,sessionid="+session.getId()+",已强制下线");
			}
		}
		return false;
	}
	
}
 