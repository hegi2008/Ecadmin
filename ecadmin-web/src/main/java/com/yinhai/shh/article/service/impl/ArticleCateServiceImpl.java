package com.yinhai.shh.article.service.impl;


import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.article.service.ArticleCateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 文章分类管理service
 */
@Service
@Transactional
public class ArticleCateServiceImpl extends BaseServiceImpl implements ArticleCateService {

    @Override
    public Map queryArticleCateById(Map param) throws Exception {
        return sqlSession.selectOne("hy.cate.manager.get", param);
    }

    @Override
    public Map queryArticleCateListByPage(Map param) throws Exception {
        Integer total = sqlSession.selectOne("hy.cate.manager.getTotal", param);
        List list = sqlSession.selectList("hy.cate.manager.getList", param);
        Map result = new HashMap();
        result.put("total", total);
        result.put("list", list);
        return result;
    }

    @Override
    public List queryArticleCateListByParentID(Map param) throws Exception {
        return sqlSession.selectList("hy.cate.manager.getList", param);
    }

    @Override
    public Map saveArticleCate(Map param) throws Exception {
        sqlSession.insert("hy.cate.manager.insert", param);
        return param;
    }

    @Override
    public Map updateArticleCate(Map param) throws Exception {
        sqlSession.update("hy.cate.manager.update", param);
        return param;
    }

    @Override
    public Map deleteArticleCate(Map param) throws Exception {
        sqlSession.delete("hy.cate.manager.delete", param);
        return param;
    }

}
