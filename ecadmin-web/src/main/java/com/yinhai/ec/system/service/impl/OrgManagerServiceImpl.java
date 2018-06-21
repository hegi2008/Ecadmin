package com.yinhai.ec.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.OrgDomain;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.ec.system.service.OrgManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrgManagerServiceImpl extends BaseServiceImpl implements OrgManagerService {
    @Override
    public List<OrgDomain> queryOrgList() {
        return sqlSession.selectList("hy.org.manager.queryOrgList");
    }

    @Override
    public List<OrgDomain> queryOrgChildren(Integer id) {
        return sqlSession.selectList("hy.org.manager.queryOrgChildren", id);
    }

    @Override
    public OrgDomain queryOrgById(Integer orgId) {
        return sqlSession.selectOne("hy.org.manager.queryOrgById", orgId);
    }

    @Override
    public void deleteOrg(Integer orgId, ResultBean bean) throws Exception {
        bean.setError(true);
        List<OrgDomain> list = new ArrayList<>();
        list = queryOrgChildren(orgId);
        for (OrgDomain org : list) {
            deleteOrg(org.getOrgId(), bean);
        }
        sqlSession.update("hy.org.manager.deleteOrg", orgId);
        bean.setError(false);
    }

    @Override
    public void saveOrg(OrgDomain orgDomain, UserDomain user, ResultBean bean) throws Exception {
        bean.setError(true);
        if (StringUtils.isEmpty(orgDomain.getOrgName())) {
            bean.setError_msg("部门名称不能为空");
        } else {
            if (orgDomain.getOrgId() != null) {
                /*拼id_path name_path org_level*/
                evalPathAndLevel(orgDomain);
                int count = sqlSession.update("hy.org.manager.updateOrg", orgDomain);
                if (count != 1) {
                    throw new BaseUpdateException("保存部门信息失败");
                }
            } else {
                orgDomain.setUserCreater(user.getUserId());
                if (orgDomain.getParentOrgId() == null) {
                    orgDomain.setParentOrgId(new Integer(0));
                }
                evalPathAndLevel(orgDomain);
                sqlSession.insert("hy.org.manager.insertOrg", orgDomain);
                orgDomain.setOrgIdPath(orgDomain.getOrgIdPath().replace("no", orgDomain.getOrgId().toString()));
                int count = sqlSession.update("hy.org.manager.updateOrg", orgDomain);
                if (count != 1) {
                    throw new BaseUpdateException("保存部门信息失败");
                }
                Map<String, Object> map = new HashMap<>();
                map.put("newId", orgDomain.getOrgId());
                bean.setLists(map);
            }
            bean.setError(false);
            bean.setError_msg("保存部门信息成功");
        }
    }

    private void evalPathAndLevel(OrgDomain orgDomain) {
        StringBuilder idSb = new StringBuilder();
        StringBuilder nameSb = new StringBuilder();
        idSb.append(orgDomain.getOrgId() != null ? orgDomain.getOrgId() : "no");
        nameSb.append(orgDomain.getOrgName());

        eval(idSb, nameSb, orgDomain);

        orgDomain.setOrgIdPath(reversByArray(idSb, "/").toString());
        orgDomain.setOrgNamePath(reversByArray(nameSb, "/").toString());
        orgDomain.setOrgLevel(idSb.toString().split("/").length);
    }

    private StringBuilder reversByArray(StringBuilder sb, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = sb.toString().split(regex);
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuilder.append(split[i]);
            if (i > 0) {
                stringBuilder.append("/");
            }
        }
        return stringBuilder;
    }

    private void eval(StringBuilder idSb, StringBuilder nameSb, OrgDomain orgDomain) {
        Integer parentOrgId = orgDomain.getParentOrgId();
        if (parentOrgId!=null && parentOrgId != 0) {
            OrgDomain org = queryOrgById(parentOrgId);
            idSb.append("/");
            nameSb.append("/");
            idSb.append(org.getOrgId());
            nameSb.append(org.getOrgName());
            eval(idSb, nameSb, org);
        }
    }

    @Override
    public void updateLeaf(ResultBean bean) {
        int count1 = sqlSession.update("hy.org.manager.updateToFalseIfNoChild");
        int count2 = sqlSession.update("hy.org.manager.updateToTrueIfExistChild");
        if (count1 < 0 || count2 < 0) {
            bean.setError(true);
            throw new BaseUpdateException("更新是否存在下级信息失败");
        }
    }
}
