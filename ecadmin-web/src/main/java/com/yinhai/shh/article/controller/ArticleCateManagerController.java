package com.yinhai.shh.article.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.shh.article.service.ArticleCateService;
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
* @package com.yinhai.ec.base.system.controller
* <p>Title: UserManagerController.java</p>
* <p>Description: 分类管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2018-6-8 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("shh/cate/cateManager")
public class ArticleCateManagerController extends BaseController{
	@Autowired
	private ArticleCateService articleCateService;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception{
		return "/shh/article/articleCateList.jsp";
	}
	@RequestMapping("/queryCateList")
	@ResponseBody
	public Object queryCateList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		articleCateService.queryForPageWithDefault("hy.cate.manager.queryListPageCate",pageParam);
		return pageParam.toDatagridMap();
	}
	@RequestMapping("/toAddArticleCate")
	public String toAddArticleCate(HttpServletRequest request) throws Exception {
		List<AppCodeDomain> types = getAppCodeByCodeString("CATE_TYPE");
		request.setAttribute("types", types);
		return "/shh/article/articleCateAdd.jsp";
	}
	@RequestMapping("/saveArticleCate")
	@ResponseBody
	public Object saveArticleCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		articleCateService.saveArticleCate(pageParam.getMap());
		return bean;
	}
	@RequestMapping("/toEditArticleCate")
	public String toEditArticleCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		Map articleCate = articleCateService.queryArticleCateById(pageParam.getMap());
		Map param = new HashMap();
		param.put("parent_id", 0);
		List cates = articleCateService.queryArticleCateListByParentID(param);
		List<AppCodeDomain> types = getAppCodeByCodeString("CATE_TYPE");
		request.setAttribute("articleCate", articleCate);
		request.setAttribute("cates", cates);
		request.setAttribute("types", types);
		return "/shh/article/articleCateEdit.jsp";
	}
	@RequestMapping("/updateArticleCate")
	@ResponseBody
	public Object updateArticleCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		articleCateService.updateArticleCate(pageParam.getMap());
		ResultBean bean = getResultBean();
		return bean;
	}
	@RequestMapping("/deleteArticleCate")
	@ResponseBody
	public Object deleteArticleCate(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		articleCateService.deleteArticleCate(pageParam.getMap());
		ResultBean bean = getResultBean();
		return bean;
	}
	@RequestMapping("/getSelectList")
	@ResponseBody
	public Object getSelectList() throws Exception {
		Map param = new HashMap();
		param.put("parent_id", 0);
		List list = articleCateService.queryArticleCateListByParentID(param);
		return list;
	}

 }
 