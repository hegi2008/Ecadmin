package com.yinhai.ec.base.webframework.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.SystemPropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

/**
* @package com.yinhai.ec.base.webframework.listener
* <p>Title: HYContextListener.java</p>
* <p>Description: 实现ContextLoaderListener</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 贵州医美达</p>
* @author cjh
* @date 2016年2月1日 下午4:07:13
* @version 1.0
 */
public class HYContextListener extends ContextLoaderListener{
	public HYContextListener(){}
	private static Logger Logger = LoggerFactory.getLogger(com.yinhai.ec.base.webframework.listener.HYContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if(Logger.isDebugEnabled()){
			Logger.debug("系统开始启动...");
		}
		super.contextInitialized(event);
		// ServletContext加入系统主题String
		ServletContext servletContext = event.getServletContext();
		SystemPropertiesManager spm = getCurrentWebApplicationContext().getBean(SystemPropertiesManager.class);
		servletContext.setAttribute(HYConst.SYSTEM_THEME,spm.getSystemTheme());
		if(Logger.isDebugEnabled()){
			Logger.debug("系统启动完成...");
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if(Logger.isDebugEnabled()){
			Logger.debug("系统开始关闭...");
		}
		super.contextDestroyed(event);
		if(Logger.isDebugEnabled()){
			Logger.debug("系统已成功关闭...");
		}
	}
}
