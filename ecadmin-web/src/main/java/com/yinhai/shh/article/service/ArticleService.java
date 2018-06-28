package com.yinhai.shh.article.service;


import com.yinhai.ec.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService {
	/**
	 * 保存文章
	 * @param param
	 * @return 
	 */
	Map<String, Object> saveArticle(Map<String, Object> param);
	/**
	 * 置顶文章
	 * @param param
	 * @return 
	 */
	Map<String, Object> updateArticleTop(Map<String, Object> param);
	/**
	 * 
	 * @Description: 查询所有文章分类
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryCategoryList(Map<String, Object> param);
	/**
	 * 
	 * @Description: 查询所有文章列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryArticleList(Map<String, Object> param);
	/**
	 * 
	 * @Description: 查询所有文章列表总条数
	 * @param param
	 * @return
	 */
	Integer queryArticleListCount(Map<String, Object> param);
	/**
	 * @Description: 根据id查询文章
	 * @param id
	 * @return
	 */
	Map<String, Object> queryArticleById(Integer id);
	/**
	 * @Description: 更新文章
	 * @param id
	 * @return
	 */
	Map<String, Object> updateArticle(Map<String, Object> param);
	
}
