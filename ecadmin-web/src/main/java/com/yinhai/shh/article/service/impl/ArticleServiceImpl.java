package com.yinhai.shh.article.service.impl;


import com.yinhai.common.IConst;
import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.article.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: 文章管理service
 */
@Service
@Transactional
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService {

	@Override
	public Map<String, Object> saveArticle(Map<String, Object> param) {
		param.remove("article_id");
		Integer id = sqlSession.insert("ec_article.insert", param);
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("error", false);
		result.put("error_msg", "新增文章成功！");
		if(param.get("article_top") != null && IConst.ARTICLE_TOP_1.toString().equals(param.get("article_top").toString())) {
			//置顶
			param.put("article_id", id);
			updateArticleTop(param);
			
		}
		param = null;
		return result;
	}

	@Override
	public Map<String, Object> updateArticleTop(Map<String, Object> param) {
		Integer max = (Integer) sqlSession.selectOne("ec_article.getMaxSortOrder", param) + 1;
		param.put("max_sort_order", max);
		if (sqlSession.update("ec_article.updateMaxSortOrder", param) > 1) {
			throw new BaseUpdateException("更新文章置顶信息大于1条!");
		}
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("error", false);
		result.put("error_msg", "更新文章置顶信息成功！");
		return result;
	}

	@Override
	public List<Map<String, Object>> queryCategoryList(Map<String, Object> param) {
		return sqlSession.selectList("ec_article_category.getCategoryList", param);
	}

	@Override
	public List<Map<String, Object>> queryArticleList(Map<String, Object> param) {
		return sqlSession.selectList("ec_article.getList", param);
	}

	@Override
	public Integer queryArticleListCount(Map<String, Object> param) {
		return (Integer) sqlSession.selectOne("ec_article.getListCount", param);
	}

	@Override
	public Map<String, Object> queryArticleById(Integer id) {
		return (Map<String, Object>) sqlSession.selectOne("ec_article.get", id);
	}

	@Override
	public Map<String, Object> updateArticle(Map<String, Object> param) {
		if (sqlSession.update("ec_article.updateAvailable", param) > 1) {
			throw new BaseUpdateException("更新文章信息大于1条!");
		}
		if(param.get("article_top") != null && IConst.ARTICLE_TOP_1.toString().equals(param.get("article_top").toString())) {
			//置顶
			updateArticleTop(param);
			
		}
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("error", false);
		result.put("error_msg", "更新文章信息成功！");
		param = null;
		return result;
	}
	
	
	
	
}
