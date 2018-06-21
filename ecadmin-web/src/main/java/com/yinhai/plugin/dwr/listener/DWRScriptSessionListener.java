package com.yinhai.plugin.dwr.listener;

import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.UserDomain;

/**
* @package com.yinhai.ec.demo.dwr.listener
* <p>Title: DWRScriptSessionListener.java</p>
* <p>Description: DWR推送 ScriptSession监听</p>
* @author cjh
* @date 2016年7月27日 下午5:15:44
* @version 1.0
 */
public class DWRScriptSessionListener implements ScriptSessionListener{
	private static final Logger LOG = LoggerFactory.getLogger(DWRScriptSessionListener.class);
	private static final String SESSION_USER_ID = "session-user-id";
	//维护一个Map key为session的Id， value为ScriptSession对象
	//public static final ConcurrentMap<String, ScriptSession> scriptSessionMap = new ConcurrentHashMap<String, ScriptSession>();

	@Override
	public void sessionCreated(ScriptSessionEvent scriptsessionevent) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		ScriptSession scriptSession = scriptsessionevent.getSession();
		if(session.getAttribute(HYConst.SESSION_USER) != null) {
			UserDomain loginUser = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			scriptSession.setAttribute(SESSION_USER_ID, loginUser.getUserId());
		}
		LOG.debug("成功向scriptSession中添加用户id");
	}

	@Override
	public void sessionDestroyed(ScriptSessionEvent scriptsessionevent) {
		
	}
}
