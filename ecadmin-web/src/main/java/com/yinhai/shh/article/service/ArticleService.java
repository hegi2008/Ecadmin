package com.yinhai.shh.article.service;


import com.yinhai.ec.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService {
    /**
     * 保存文章
     *
     * @param param
     * @return
     */
    Map<String, Object> saveArticle(Map<String, Object> param);

    /**
     * 置顶文章
     *
     * @param param
     * @return
     */
    Map<String, Object> updateArticleTop(Map<String, Object> param);

    /**
     * @param param
     * @return
     * @Description: 查询所有文章分类
     */
    List<Map<String, Object>> queryCategoryList(Map<String, Object> param);

    /**
     * @param param
     * @return
     * @Description: 查询所有文章列表
     */
    List<Map<String, Object>> queryArticleList(Map<String, Object> param);

    /**
     * @param param
     * @return
     * @Description: 查询所有文章列表总条数
     */
    Integer queryArticleListCount(Map<String, Object> param);

    /**
     * @param id
     * @return
     * @Description: 根据id查询文章
     */
    Map<String, Object> queryArticleById(Integer id);

    /**
     * @param id
     * @return
     * @Description: 更新文章
     */
    Map<String, Object> updateArticle(Map<String, Object> param);

}
