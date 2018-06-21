package com.yinhai.shh.account.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.account.domain.AccountDomain;
import com.yinhai.shh.account.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: UserManagerController.java</p>
* <p>Description: 账户管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2018-6-8 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("shh/account/accountManager")
public class AccountManagerController extends BaseController{
	@Autowired
	private AccountManagerService accountManagerService;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception{
		List<AppCodeDomain> qd_type = getAppCodeByCodeString("qd_type");
		map.put("qd_type", qd_type);
		return "/shh/account/accountManager.jsp";
	}
	
	@RequestMapping("/queryAccountList")
	@ResponseBody
	public Object queryAccountList(HttpServletRequest request) throws Exception{
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("create_time"))){
			String create_time = pageParam.getString("create_time");
			pageParam.put("startDate", create_time.split("~")[0].trim()+" 00:00:00");
			pageParam.put("endDate", create_time.split("~")[1].trim()+" 23:59:59");
		}
		accountManagerService.queryForPageWithDefault("hy.account.manager.queryListPageAccountManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/createAccount")
	public String createAccount(ModelMap modelMap, HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> qd_type = getAppCodeByCodeString("qd_type");
		modelMap.put("qd_type", qd_type);
		if(pageParam.get("yad901") != null && pageParam.get("yad961") != null){
			AccountDomain account = accountManagerService.querySingleAccount(pageParam);
			modelMap.put("smallTitle", "账户编辑");
			modelMap.put("account", account);
			modelMap.put("edit", "1");
		}else{
			modelMap.put("smallTitle", "账户新增");
		}
		return "/shh/account/createAccount.jsp";
	}
	
	@RequestMapping("/saveAccount")
	@ResponseBody
	public Object saveAccount(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		UserDomain loginUser = getUserInfo();
		accountManagerService.saveAccount(pageParam,bean,loginUser);
		return bean;
	}
	
	@RequestMapping("/updateAccount")
	@ResponseBody
	public Object updateAccount(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		accountManagerService.updateAccount(pageParam,bean);
		return bean;
	}

 }
 