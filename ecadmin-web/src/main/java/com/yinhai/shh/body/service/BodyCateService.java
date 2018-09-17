package com.yinhai.shh.body.service;

import com.yinhai.ec.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface BodyCateService extends BaseService {

    /**
     * 通过ID查询一条身体分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map queryBodyCateById(Map param) throws Exception;

    /**
     * 查询身体分类列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map queryBodyCateListByPage(Map param) throws Exception;

    /**
     * 通过parent_id查询子分类列表
     *
     * @param param
     * @return
     * @throws Exception
     */
    List queryBodyCateListByParentID(Map param) throws Exception;

    /**
     * 新增身体分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map saveBodyCate(Map param) throws Exception;

    /**
     * 修改身体分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map updateBodyCate(Map param) throws Exception;

    /**
     * 删除一条身体分类
     *
     * @param param
     * @return
     * @throws Exception
     */
    Map deleteBodyCate(Map param) throws Exception;
}
