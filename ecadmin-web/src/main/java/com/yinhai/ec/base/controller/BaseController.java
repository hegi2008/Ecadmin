package com.yinhai.ec.base.controller;  

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yinhai.ec.base.cache.ehcache.AppCodeTemplate;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.domain.UserDomain;

/**
* @package com.yinhai.ec.base.controller
* <p>Title: BaseController.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 贵州医美达</p>
* @author cjh
* @date 2016年1月25日 下午2:09:33
* @version 1.0
 */
public class BaseController {
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getModelAndView 方法 
	  * @describe <p>方法说明:获取视图对象</p>
	  * @return ModelAndView 
	  * @author cjh
	  * @date 2015-12-31
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getPageParam 方法 
	  * @describe <p>方法说明:获取参数对象 默认封装request参数 以及当前登陆用户信息</p>
	  * <p>并且start pageSize 是针对ec grid </p>
	  * @return PageParam 
	  * @author cjh
	  * @date 2015-12-31
	 */
	public PageParam getPageParam(HttpServletRequest request) {
		PageParam pageParam = new PageParam(request);
		if(getCurrentSession() != null && getCurrentSession().getAttribute(HYConst.SESSION_USER) != null){
			pageParam.setUserInfo(getUserInfo());
		}
		pageParam.put("start",pageParam.getStart());
		pageParam.put("pageSize",pageParam.getPageSize());
		return pageParam;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getOffsetPageParam 方法 
	  * @describe <p>方法说明:获取参数对象 默认封装request参数 以及当前登陆用户信息</p>
	  * <p>并且start pageSize 是针对bootstrap table </p>
	  * @return PageParam 
	  * @author cjh
	  * @date 2015-12-31
	 */
	public PageParam getOffsetPageParam(HttpServletRequest request) {
		PageParam pageParam = new PageParam(request);
		if(getCurrentSession() != null && getCurrentSession().getAttribute(HYConst.SESSION_USER) != null){
			pageParam.setUserInfo(getUserInfo());
		}
		pageParam.put("start",pageParam.getOffsetStart());
		pageParam.put("pageSize",pageParam.getOffsetPageSize());
		return pageParam;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getResultBean 方法 
	  * @describe <p>方法说明:异步方法需要返回的标准response bean</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016-1-8
	 */
	public ResultBean getResultBean() {
		return new ResultBean();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSecurityManager 方法 
	  * @describe <p>方法说明:获取ShiroSecurityManager</p>
	  * @return SecurityManager 
	  * @author cjh
	  * @date 2016年3月22日 上午10:58:17
	 */
	public SecurityManager getShiroSecurityManager() {
		return SecurityUtils.getSecurityManager(); 
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSubject 方法 
	  * @describe <p>方法说明:获取shiro管理的subject</p>
	  * @return Session 
	  * @author cjh
	  * @date 2016-1-7
	 */
	public Subject getShiroSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getShiroSession 方法 
	  * @describe <p>方法说明:获取shiro管理的session</p>
	  * @return Session 
	  * @author cjh
	  * @date 2016-1-7
	 */
	public Session getCurrentSession() {
		return getShiroSubject().getSession(false);
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getUserInfo 方法 
	  * @describe <p>方法说明:获取登录用户信息</p>
	  * @return UserDomain 
	  * @author cjh
	  * @date 2016-1-12
	 */
	public UserDomain getUserInfo() {
		return (UserDomain) getCurrentSession().getAttribute(HYConst.SESSION_USER);
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static Logger getLogger(Class<?> class1) {
		return LoggerFactory.getLogger(class1);
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getAppCodeByCodeString 方法 
	  * @describe <p>方法说明:根据code_string获取码表list</p>
	  * @return List<AppCodeDomain> 
	  * @author cjh
	  * @throws Exception 
	  * @date 2016年2月2日 下午12:54:48
	 */
	public List<AppCodeDomain> getAppCodeByCodeString(String code_string) throws Exception {
		if(StringUtils.isEmpty(code_string)){
			return null;
		}
		return appCodeTemplate.getAppCodeByCodeString(code_string.toUpperCase(Locale.ENGLISH));
	}
	
	public AppCodeTemplate getAppCodeTemplate() {
		return appCodeTemplate;
	}
	
	@Autowired
	private AppCodeTemplate appCodeTemplate;
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
}
 