package com.yinhai.shh.body.service.impl;


import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.body.service.BodyCateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 身体分类管理service
 */
@Service
@Transactional
public class BodyCateServiceImpl extends BaseServiceImpl implements BodyCateService {

    @Override
    public Map queryBodyCateById(Map param) throws Exception {
        return sqlSession.selectOne("hy.body.manager.get", param);
    }

    @Override
    public Map queryBodyCateListByPage(Map param) throws Exception {
        Integer total = sqlSession.selectOne("hy.body.manager.getTotal", param);
        List list = sqlSession.selectList("hy.body.manager.getList", param);
        Map result = new HashMap();
        result.put("total", total);
        result.put("list", list);
        return result;
    }

    @Override
    public List queryBodyCateListByParentID(Map param) throws Exception {
        return sqlSession.selectList("hy.body.manager.getList", param);
    }

    @Override
    public Map saveBodyCate(Map param) throws Exception {
        sqlSession.insert("hy.body.manager.insert", param);
        return param;
    }

    @Override
    public Map updateBodyCate(Map param) throws Exception {
        sqlSession.update("hy.body.manager.update", param);
        return param;
    }

    @Override
    public Map deleteBodyCate(Map param) throws Exception {
        sqlSession.delete("hy.body.manager.delete", param);
        return param;
    }

}
