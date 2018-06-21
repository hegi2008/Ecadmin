package com.yinhai.ec.system.controller;  

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.system.service.MenuManagerService;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: MenuManagerController.java</p>
* <p>Description: 菜单管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-12 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("system/menu/menuManager")
public class MenuManagerController extends BaseController{
	@Autowired
	private MenuManagerService service;
	
	@RequestMapping("/default")
	public String execute() throws Exception{
		return "/system/menuManager.jsp";
	}
	
	@RequestMapping("/queryMenuList")
	@ResponseBody
	public Object queryMenuList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		service.queryMenuList(pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/saveMenu")
	@ResponseBody
	public Object saveMenu(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		if (pageParam.get("node") == null) {
			bean.setError(true);
			bean.setError_msg("保存失败:未获取到需要操作的菜单信息");
		}else{
			String node = pageParam.get("node").toString();
			Map<String, Object> param = JSONObject.parseObject(node);
			UserDomain user = getUserInfo();
			service.saveMenu(param,user,bean);
		}
		return bean;
	}
	
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public Object deleteMenu(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		service.deleteMenu(pageParam,bean);
		return bean;
	}
 }
 