package com.yinhai.ec.base.controller;  

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.yinhai.ec.base.exception.IncorrectCaptchaException;
import com.yinhai.ec.base.service.online.OnlineService;
import com.yinhai.ec.base.service.user.UserService;
import com.yinhai.ec.base.session.dao.HYBaseSessionDao;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.base.util.SystemPropertiesManager;
import com.yinhai.ec.common.domain.UserDomain;
  
/**
* @package com.yinhai.ec.base.controller
* <p>Title: LoginController.java</p>
* <p>Description: 登陆入口</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2015-12-31 上午9:56:01
* @version 1.0
 */
@Controller
public class LoginController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OnlineService onlineService;
	@Autowired
	private UserService userservice;
	@Autowired
	private SystemPropertiesManager spm;
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method login 方法 
	  * @describe <p>方法说明:跳转到登陆页面</p>
	  * @return String 
	  * @author cjh
	  * @date 2016-1-6
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		PageParam param = getPageParam(request);
		if (param.get("forceLogout") != null) {
			request.setAttribute("login_error_msg", "您已经被管理员强制退出，请重新登录");
		}
		if(param.get("repeatLogin") != null){
			request.setAttribute("login_error_msg", "该账号已在别的客户端登陆,不能重复登录");
		}
		String systemTheme = spm.getSystemTheme();
		return "/UI/themes/"+systemTheme+"/login.jsp";
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method doLogin 方法 
	  * @describe <p>方法说明:登陆主方法</p>
	  * @return Object 
	  * @author cjh
	  * @date 2015-12-31
	 */
	@RequestMapping(value="/doLogin",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object doLogin(HttpServletRequest request) {
		PageParam param = getPageParam(request);
		//Boolean rememberMe = Boolean.valueOf(param.get("rememberMe").toString());
		param.put("error", true);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(param.getString("username"), param.getString("password"));
		// shiro登陆,这里必须try catch
		try {
			//if (rememberMe) {
				//token.setRememberMe(rememberMe);
			//}
			doCaptchaValidate(request,param.getString("code"));
			
			subject.login(token);
			param.put("error", false);
			logger.debug("用户:"+param.getString("username")+"认证成功,已成功登陆系统");
			Session session = subject.getSession(false);
			UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			if(user != null && session.getId() != null){
				onlineService.executeOnline(session);
			}
			
			// 登录成功以后,验证是否可以重复登录
			/*Boolean repeatLogin = sessionDao.getRepeatLogin();
			if (!repeatLogin) {
				Collection<Session> sessions = sessionDao.getActiveSessions();
				for (Session resession : sessions) {
					if(resession == null){
						continue;
					}
					if(!resession.getId().equals(session.getId())){
						UserDomain reuser = (UserDomain) resession.getAttribute(HYConst.SESSION_USER);
						if(reuser != null && user != null && user.getUserId().equals(reuser.getUserId())){
							resession.setAttribute(HYConst.SESSION_REPEAT_LOGIN_KEY, true);
						}
					}
				}
			}*/
		} catch (AuthenticationException e) {
			String msg = e.getMessage();
			if(msg != null){
				param.put("error_msg", msg);
			}else{
				param.put("error_msg", "身份验证未通过,不能登录");
			}
			if (e instanceof IncorrectCredentialsException) {
				try {
					userservice.UpdateUserPwdWrongTime(param.getString("username"), HYConst.STATUS_YES);
					if (logger.isErrorEnabled()) {
						logger.error("user Password is wrong:"+ param.getString("username"));
					}
					param.put("error_msg", "密码输入错误");
				} catch (Exception e1) {
					if (logger.isErrorEnabled()) {
						logger.error("UpdateUserPwdWrongTime with an error: ",e);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				param.put("error_msg", "登陆出错");
				logger.error("登陆出错",e);
			}
		}
		return param;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method doCaptchaValidate 方法 
	  * @describe <p>方法说明:验证码验证</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月25日 上午10:04:17
	 */
    private void doCaptchaValidate(HttpServletRequest request, String code) {
        //比对
        if(spm.isNeedAuthCode()){
        	//session中的图形码字符串  
        	String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);  
        	if (captcha != null && !captcha.equalsIgnoreCase(code)) {  
                throw new IncorrectCaptchaException("验证码错误!");
            }
        }
    }  
	
	@RequestMapping("/changeUserPassword")
	@ResponseBody
	public Object changeUserPassword(HttpServletRequest request) throws Exception{
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		userservice.updateUserPassword(pageParam,bean);
		return bean;
	}
}
 