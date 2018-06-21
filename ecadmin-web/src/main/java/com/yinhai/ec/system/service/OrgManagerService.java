package com.yinhai.ec.system.service;

import java.util.List;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.OrgDomain;
import com.yinhai.ec.common.domain.UserDomain;


public interface OrgManagerService extends BaseService {
    /**
     * @describe <p>方法说明:获取部门list</p>
     * @author tanqingping
     * @date 2016-4-25
     * @return 部门列表
     */
    List<OrgDomain> queryOrgList();
    /**
     * @describe <p>方法说明:获取部门的子部门集合</p>
     * @author tanqingping
     * @date 2016-4-25
     * @return 部门列表
     */
    List<OrgDomain> queryOrgChildren(Integer id);
    /**
     * @describe <p>方法说明:获取某个部门信息</p>
     * @author tanqingping
     * @date 2016-4-25
     * @return 部门
     */
    OrgDomain queryOrgById(Integer id);
    /**
     * @describe <p>方法说明:删除部门(及其所有子部门)</p>
     * @author tanqingping
     * @date 2016-4-25
     */
    void deleteOrg(Integer id,ResultBean bean) throws Exception;
    /**
     * @describe <p>方法说明:保存部门信息</p>
     * @author tanqingping
     * @date 2016-4-25
     */
    void saveOrg(OrgDomain orgDomain, UserDomain user, ResultBean bean) throws Exception;
    /**
     * @describe <p>方法说明:更新数据后，检查部门是否存在子级并作处理</p>
     * @author tanqingping
     * @date 2016-4-27
     */
    void updateLeaf(ResultBean bean)throws Exception;
}
