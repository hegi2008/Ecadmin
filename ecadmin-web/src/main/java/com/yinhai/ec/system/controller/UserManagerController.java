package com.yinhai.ec.system.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.ec.system.service.UserManagerService;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: UserManagerController.java</p>
* <p>Description: 用户管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-12 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("system/user/userManager")
public class UserManagerController extends BaseController{
	@Autowired
	private UserManagerService userManagerService;
	
	@RequestMapping("/default")
	public String execute() throws Exception{
		return "/system/userManager.jsp";
	}
	
	@RequestMapping("/queryUserList")
	@ResponseBody
	public Object queryUserList(HttpServletRequest request) throws Exception{
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("user_create_time"))){
			String user_create_time = pageParam.getString("user_create_time");
			pageParam.put("startDate", user_create_time.split("~")[0].trim());
			pageParam.put("endDate", user_create_time.split("~")[1].trim());
		}
		userManagerService.queryForPageWithDefault("hy.user.manager.queryListPageUserManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/createUser")
	public String createUser(ModelMap modelMap, HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(pageParam.get("user_id") != null){
			UserDomain user = userManagerService.querySingleUser(pageParam);
			modelMap.put("smallTitle", "用户编辑");
			modelMap.put("user", user);
		}else{
			modelMap.put("smallTitle", "用户新增");
		}
		return "/system/createUser.jsp";
	}
	
	@RequestMapping("/saveUser")
	@ResponseBody
	public Object saveUser(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		UserDomain loginUser = getUserInfo();
		userManagerService.saveUser(pageParam,bean,loginUser);
		return bean;
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public Object updateUser(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		userManagerService.updateUser(pageParam,bean);
		return bean;
	}
	
	
	@RequestMapping("/updateResetPassword")
	@ResponseBody
	public Object updateResetPassword(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		userManagerService.updateResetPassword(pageParam,bean);
		return bean;
	}
 }
 