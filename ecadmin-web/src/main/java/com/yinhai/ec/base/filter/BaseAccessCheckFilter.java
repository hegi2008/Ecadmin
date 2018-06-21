package com.yinhai.ec.base.filter;  

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.ResultBean;
  
/**
* @package com.yinhai.ec.base.filter
* <p>Title: BaseAccessCheckFilter.java</p>
* <p>Description: URL访问权限拦截器</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-7 下午4:19:08
* @version 1.0
 */
public class BaseAccessCheckFilter extends AccessControlFilter{
	private static final Logger logger = LoggerFactory.getLogger(com.yinhai.ec.base.filter.BaseAccessCheckFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object object) throws Exception {
		HttpServletRequest servletRequest = (HttpServletRequest) request; 
		String path = servletRequest.getServletPath();
		if (path.matches(HYConst.NO_INTERCEPTOR_PATH)) {
			return true;
		}else{
			//shiro管理的session
			if (path.startsWith("/")) {
				path = path.substring(1, path.length());
			}
			path = path.replaceAll("/", ":");
			Subject subject = getSubject(request, response);
			Session session = subject.getSession(false);
			if (session == null) {
				return false;
			}
			return subject.isPermitted(path);
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest servletRequest = (HttpServletRequest) request; 
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String path = servletRequest.getServletPath();
		Subject subject = getSubject(request, response);
		ResultBean bean = new ResultBean();
		bean.setError(true);
		bean.setError_code(HYConst.ACCESS_DENIED_CODE);
		if (subject.getPrincipal() == null) {  
            if (com.yinhai.ec.common.util.WebUtils.isAjax(servletRequest)) {
            	bean.setError_msg("您尚未登录或登录时间过长,请重新登录!");
            	com.yinhai.ec.common.util.WebUtils.sendJson(servletResponse, JSONArray.toJSONString(bean));
            } else {  
                saveRequestAndRedirectToLogin(request, response);  
            }
            if (logger.isWarnEnabled()) {
    			logger.warn("Session is timeout for request url ["+path+"]");
    		}
        } else {  
            if (com.yinhai.ec.common.util.WebUtils.isAjax(servletRequest)) { 
            	bean.setError_msg("您没有足够的权限执行该操作!");
            	com.yinhai.ec.common.util.WebUtils.sendJson(servletResponse, JSONArray.toJSONString(bean));
            } else {  
            	WebUtils.issueRedirect(request, response, HYConst.ACCESS_DENIED);
            }
            if (logger.isWarnEnabled()) {
    			logger.warn("Access denied for url ["+path+"]");
    		}
        }
		return false;
	}
}
 