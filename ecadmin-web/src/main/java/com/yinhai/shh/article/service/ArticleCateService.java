package com.yinhai.shh.article.service;

import com.yinhai.ec.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ArticleCateService extends BaseService {

    /**
     * 通过ID查询一条文章分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map queryArticleCateById(Map param) throws Exception;

    /**
     * 查询文章分类列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map queryArticleCateListByPage(Map param) throws Exception;

    /**
     * 通过parent_id查询子分类列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List queryArticleCateListByParentID(Map param) throws Exception;

    /**
     * 新增文章分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map saveArticleCate(Map param) throws Exception;

    /**
     * 修改文章分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map updateArticleCate(Map param) throws Exception;

    /**
     * 删除一条文章分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map deleteArticleCate(Map param) throws Exception;
}
