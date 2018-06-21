package com.yinhai.ec.base.controller;  

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinhai.ec.base.session.dao.HYBaseSessionDao;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.yinhai.ec.base.controller
* <p>Title: LogoutController.java</p>
* <p>Description: 退出登录</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-5 下午3:30:09
* @version 1.0
 */
@Controller
public class LogoutController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionDAO sessionDao;
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method doLogin 方法 
	  * @describe <p>方法说明:登陆主方法</p>
	  * @return Object 
	  * @author cjh
	  * @date 2015-12-31
	 */
	@RequestMapping("/logout")
	public String logout() {
		Subject subject = getShiroSubject();
		Session session = subject.getSession();
		if (session != null && session.getAttribute(HYConst.SESSION_USER) != null) {
			UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			subject.logout();
			logger.debug("用户["+user.getUserName()+"] 成功退出系统");
		}
		return "redirect:/login";
	}
	
	@RequestMapping("/forceLogout")
	@ResponseBody
	public Object forceLogout(HttpServletRequest request) {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		String session_id = pageParam.getString("session_id");
		// 获取当前session
		Session currentSession = getCurrentSession();
		UserDomain domain = null;
		Session session = sessionDao.readSession(session_id);
		if (!StringUtils.isEmpty(session_id)) {
			if (session.getId().equals(currentSession.getId().toString())) {
				bean.setError(true);
				bean.setError_msg("自己不能强制退出自己!");
				return bean;
			}
			session.setAttribute(HYConst.SESSION_FORCE_LOGOUT_KEY, true);
			domain = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
		}
		bean.setSuccess_msg(domain == null ? "":domain.getUserName()+"已强制退出");
        return bean;
	}
}
 