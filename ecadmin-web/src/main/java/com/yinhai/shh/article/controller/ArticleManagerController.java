package com.yinhai.shh.article.controller;

import com.yinhai.common.*;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: UserManagerController.java</p>
* <p>Description: 文章管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2018-6-8 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("shh/article/articleManager")
public class ArticleManagerController extends BaseController{
	@Autowired
	private ArticleService articleService;

	//文章列表
	@RequestMapping("/default")
	public String execute(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		int orgId = pageParam.getUserInfo().getOrgId();
		if(orgId == 1) { //有审核权限
			request.setAttribute("audit", "1");
		}
		return "/shh/article/articleList.jsp";
	}

	//文章列表list
	@RequestMapping("/artilceList")
	@ResponseBody
	public Object artilceList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("add_time_value"))) {
			String time = pageParam.get("add_time_value").toString();
			pageParam.put("add_time_start", time.substring(0, time.indexOf("~")).trim());
			pageParam.put("add_time_end", time.substring(time.indexOf("~") + 1, time.length()).trim());
		}
		articleService.queryForPageWithDefault("hy.article.manager.queryListPageArticle",pageParam);
		return pageParam.toDatagridMap();
	}
	//文章分类
	@RequestMapping("/getCategorys")
	@ResponseBody
	public Object getCategorys(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = articleService.queryCategoryList(null);
		List treeList = TreeUtil.getTreeList(list, IConst.ARTICLE_CATE_TOPID + "", new HashMap(), "children");
		return treeList;
	}

	//跳转到新增新闻
	@RequestMapping("/toArticle")
	public String toArticle(HttpServletRequest request) throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("article_id");
		if(ValidateUtil.isNotEmpty(id)) {
			Map<String, Object> map = articleService.queryArticleById(Integer.parseInt(id));
			if(map.get("file_url") != null && !"".equals(map.get("file_url").toString())) {
				String file_url_value = ConfigUtil.getFileServerDomain() + map.get("file_url").toString();
				map.put("file_url_value", file_url_value);
			}
			request.setAttribute("ae", map);
		}
		request.setAttribute("type", type);
		if(type != null && "preview".equals(type)) {
			return "/shh/article/previewArticle.jsp";
		}
		return "/shh/article/addArticle.jsp";
	}
	//更新文章
	@RequestMapping("/updateArticle")
	@ResponseBody
	@SuppressWarnings("unused")
	public Object updateArticle(HttpServletRequest request) throws Exception {
		String ss = request.getParameter("content");
		Map param = WebUtils.getScriptFilterParam(request);
		if(param.get("status") == null) {
			param.put("status", 1); //编辑后重新审核文章
		}
		Map result = articleService.updateArticle(param);
		result.put("type", "edit");
		return result;
	}

	//图片上传
	@RequestMapping("/upLoadFile")
	@ResponseBody
	public Object upLoadFile(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//todo 待修改
		String imgFileFileName = request.getParameter("imgFileFileName");
		File imgFile = new File("");
		String fileName = WebUtils.createFileName(WebUtils.getFileSuffix(imgFileFileName));
		String filePath = ConfigUtil.getEcArticleUploadPath() + fileName;
		String fileUrl = ConfigUtil.getEcArticleVisitPath() + fileName;
		result.put("error", 0);
		try {
			VfsNewFileUtil.saveFile(filePath, imgFile);
			result.put("url", ConfigUtil.getFileServerDomain() + fileUrl);
		} catch (Exception e) {
			result.put("error", 1); //上传文件出错
			result.put("message", "上传文件出错……");
			//e.printStackTrace();
		}
		return result;

	}

	//图片上传
	@RequestMapping("/upLoadFuJian")
	@ResponseBody
	public Object upLoadFuJian(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//todo 待修改
		String imgFileFileName = request.getParameter("imgFileFileName");
		File imgFile = new File("");
		String fileName = WebUtils.createFileName(WebUtils.getFileSuffix(imgFileFileName));
		String filePath = ConfigUtil.getEcFuJianUploadPath() + fileName;
		String fileUrl = ConfigUtil.getEcFuJianVisitPath() + fileName;
		result.put("error", 0);
		try {
			VfsNewFileUtil.saveFile(filePath, imgFile);
			result.put("url", ConfigUtil.getFileServerDomain() + fileUrl);
			result.put("file_url",  fileName);
		} catch (Exception e) {
			result.put("error", 1); //上传文件出错
			result.put("message", "上传文件出错……");
			//e.printStackTrace();
		}
		return result;

	}

	//保存文章
	@RequestMapping("/saveArticle")
	@ResponseBody
	public Object saveArticle(HttpServletRequest request) throws Exception {
		Map<String, Object> param = WebUtils.getScriptFilterParam(request);
		PageParam pageParam = getPageParam(request);
		param.put("status", IConst.AD_STATUS_1);
		param.put("add_username", pageParam.getUserInfo().getUserName());
		param.put("status", IConst.AD_STATUS_1);
		param.put("count", 0); //点击次数
		param.put("sort_order", 1); //排序
		Map<String, Object> result = articleService.saveArticle(param);
		result.put("type", "add");
		return result;
	}

 }
 