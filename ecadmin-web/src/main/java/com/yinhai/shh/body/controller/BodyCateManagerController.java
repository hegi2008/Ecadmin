package com.yinhai.shh.body.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.shh.body.service.BodyCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 身体分类管理
 */
@Controller
@RequestMapping("shh/body/bodyManager")
public class BodyCateManagerController extends BaseController{
	@Autowired
	private BodyCateService bodyCateService;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception{
		return "/shh/body/bodyCateList.jsp";
	}
	@RequestMapping("/queryCateList")
	@ResponseBody
	public Object queryCateList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		bodyCateService.queryForPageWithDefault("hy.body.manager.queryListPageCate",pageParam);
		return pageParam.toDatagridMap();
	}
	@RequestMapping("/toAddBodyCate")
	public String toAddBodyCate(HttpServletRequest request) throws Exception {
		List<AppCodeDomain> types = getAppCodeByCodeString("BODYTYPE");
		request.setAttribute("types", types);
		return "/shh/body/bodyCateAdd.jsp";
	}
	@RequestMapping("/saveBodyCate")
	@ResponseBody
	public Object saveBodyCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		bodyCateService.saveBodyCate(pageParam.getMap());
		return bean;
	}
	@RequestMapping("/toEditBodyCate")
	public String toEditBodyCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		Map bodyCate = bodyCateService.queryBodyCateById(pageParam.getMap());
		Map param = new HashMap();
		param.put("parent_id", 0);
		List cates = bodyCateService.queryBodyCateListByParentID(param);
		List<AppCodeDomain> types = getAppCodeByCodeString("BODYTYPE");
		request.setAttribute("bodyCate", bodyCate);
		request.setAttribute("cates", cates);
		request.setAttribute("types", types);
		return "/shh/body/bodyCateEdit.jsp";
	}
	@RequestMapping("/updateBodyCate")
	@ResponseBody
	public Object updateBodyCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		bodyCateService.updateBodyCate(pageParam.getMap());
		ResultBean bean = getResultBean();
		return bean;
	}
	@RequestMapping("/deleteBodyCate")
	@ResponseBody
	public Object deleteBodyCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		bodyCateService.deleteBodyCate(pageParam.getMap());
		ResultBean bean = getResultBean();
		return bean;
	}
	@RequestMapping("/getSelectList")
	@ResponseBody
	public Object getSelectList() throws Exception {
		Map param = new HashMap();
		param.put("parent_id", 0);
		List list = bodyCateService.queryBodyCateListByParentID(param);
		return list;
	}

 }
 