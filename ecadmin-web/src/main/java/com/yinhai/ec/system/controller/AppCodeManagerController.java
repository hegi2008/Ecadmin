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
import com.yinhai.ec.system.service.AppCodeService;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: AppCodeManagerController.java</p>
* <p>Description: 码表管理</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 贵州医美达</p>
* @author cjh
* @date 2016年4月11日 上午9:45:28
* @version 1.0
 */
@Controller
@RequestMapping("system/appcode/appcodeManager")
public class AppCodeManagerController extends BaseController{
	
	@Autowired
	private AppCodeService service;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception {
		map.put("code_type", getAppCodeByCodeString("code_type"));
		return "/system/appcodeManager.jsp";
	}
	
	@RequestMapping("/queryCodeList")
	@ResponseBody
	public Object queryCodeList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		service.queryForPageWithDefault("hy.appcode.queryListPageAppCode", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/saveCode")
	@ResponseBody
	public Object saveCode(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		UserDomain loginUser = getUserInfo();
		service.saveCode(pageParam,bean,loginUser);
		return bean;
	}
	
	@RequestMapping("/deleteCode")
	@ResponseBody
	public Object deleteCode(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		service.deleteCode(pageParam,bean);
		return bean;
	}
	
	@RequestMapping("/updateCodeCache")
	@ResponseBody
	public Object updateCodeCache(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		getAppCodeTemplate().updateCodeCache(request.getParameter("codeString"));
		bean.setSuccess_msg("刷新完成");
		return bean;
	}
	
	@RequestMapping("/updateAllCodeCache")
	@ResponseBody
	public Object updateAllCodeCache() throws Exception {
		ResultBean bean = getResultBean();
		getAppCodeTemplate().updateAllCodeCache();
		bean.setSuccess_msg("全部码表刷新完成");
		return bean;
	}
}
