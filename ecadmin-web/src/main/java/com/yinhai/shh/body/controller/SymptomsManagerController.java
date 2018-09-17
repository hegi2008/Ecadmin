package com.yinhai.shh.body.controller;

import com.yinhai.common.*;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.body.service.SymptomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 症状管理
 */
@Controller
@RequestMapping("shh/symptoms/symptomsManager")
public class SymptomsManagerController extends BaseController{
	@Autowired
	private SymptomsService symptomsService;

	//症状列表
	@RequestMapping("/default")
	public String execute() throws Exception {
		return "/shh/symptoms/symptomsList.jsp";
	}

	//症状列表list
	@RequestMapping("/symptomsList")
	@ResponseBody
	public Object symptomsList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("add_time_value"))) {
			String time = pageParam.get("add_time_value").toString();
			pageParam.put("add_time_start", time.substring(0, time.indexOf("~")).trim());
			pageParam.put("add_time_end", time.substring(time.indexOf("~") + 1, time.length()).trim());
		}
		symptomsService.queryForPageWithDefault("hy.symptoms.manager.queryListPageSymptoms",pageParam);
		return pageParam.toDatagridMap();
	}
	//部位
	@RequestMapping("/getBodys")
	@ResponseBody
	public Object getBodys() throws Exception {
		List<Map<String, Object>> list = symptomsService.queryBodyList(null);
		List treeList = TreeUtil.getTreeList(list, 0 + "", new HashMap(), "children");
		return treeList;
	}

	//跳转到新增症状
	@RequestMapping("/toSymptoms")
	public String toSymptoms(HttpServletRequest request) throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("symptoms_id");
		if(ValidateUtil.isNotEmpty(id)) {
			Map<String, Object> map = symptomsService.querySymptomsById(Integer.parseInt(id));
			request.setAttribute("ae", map);
		}
		request.setAttribute("type", type);
		if(type != null && "preview".equals(type)) {
			return "/shh/symptoms/previewSymptoms.jsp";
		}
		return "/shh/symptoms/addSymptoms.jsp";
	}
	//更新症状
	@RequestMapping("/updateSymptoms")
	@ResponseBody
	@SuppressWarnings("unused")
	public Object updateSymptoms(HttpServletRequest request) throws Exception {
		Map param = WebUtils.getScriptFilterParam(request);
		Map result = symptomsService.updateSymptoms(param);
		result.put("type", "edit");
		return result;
	}


	//保存症状
	@RequestMapping("/saveSymptoms")
	@ResponseBody
	public Object saveSymptoms(HttpServletRequest request) throws Exception {
		Map<String, Object> param = WebUtils.getScriptFilterParam(request);
		PageParam pageParam = getPageParam(request);
		param.put("status", IConst.AD_STATUS_1);
		param.put("add_username", pageParam.getUserInfo().getUserName());
		Map<String, Object> result = symptomsService.saveSymptoms(param);
		result.put("type", "add");
		return result;
	}

 }
 