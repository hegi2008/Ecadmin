package com.yinhai.shh.body.service;


import com.yinhai.ec.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SymptomsService extends BaseService {
    /**
     * 保存导诊症状
     *
     * @param param
     * @return
     */
    Map<String, Object> saveSymptoms(Map<String, Object> param);


    /**
     * @param param
     * @return
     * @Description: 查询所有部位
     */
    List<Map<String, Object>> queryBodyList(Map<String, Object> param);

    /**
     * @param param
     * @return
     * @Description: 查询所有症状列表
     */
    List<Map<String, Object>> querySymptomsList(Map<String, Object> param);

    /**
     * @param param
     * @return
     * @Description: 查询所有症状列表总条数
     */
    Integer querySymptomsListCount(Map<String, Object> param);

    /**
     * @param id
     * @return
     * @Description: 根据id查询症状
     */
    Map<String, Object> querySymptomsById(Integer id);

    /**
     * @param param
     * @return
     * @Description: 更新症状
     */
    Map<String, Object> updateSymptoms(Map<String, Object> param);

}
