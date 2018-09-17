package com.yinhai.shh.body.service.impl;


import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.body.service.SymptomsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 症状管理service cjh
 */
@Service
@Transactional
public class SymptomsServiceImpl extends BaseServiceImpl implements SymptomsService {

    @Override
    public Map<String, Object> saveSymptoms(Map<String, Object> param) {
        sqlSession.insert("hy.symptoms.manager.insert", param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", false);
        result.put("error_msg", "新增症状成功！");
        return result;
    }


    @Override
    public List<Map<String, Object>> queryBodyList(Map<String, Object> param) {
        return sqlSession.selectList("hy.body.manager.getCategoryList", param);
    }

    @Override
    public List<Map<String, Object>> querySymptomsList(Map<String, Object> param) {
        return sqlSession.selectList("hy.symptoms.manager.getList", param);
    }

    @Override
    public Integer querySymptomsListCount(Map<String, Object> param) {
        return (Integer) sqlSession.selectOne("hy.symptoms.manager.getListCount", param);
    }

    @Override
    public Map<String, Object> querySymptomsById(Integer id) {
        return (Map<String, Object>) sqlSession.selectOne("hy.symptoms.manager.get", id);
    }

    @Override
    public Map<String, Object> updateSymptoms(Map<String, Object> param) {
        if (sqlSession.update("hy.symptoms.manager.updateAvailable", param) > 1) {
            throw new BaseUpdateException("更新症状信息大于1条!");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", false);
        result.put("error_msg", "更新症状信息成功！");
        return result;
    }


}
