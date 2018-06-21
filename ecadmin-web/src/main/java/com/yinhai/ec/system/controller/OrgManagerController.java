package com.yinhai.ec.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.OrgDomain;
import com.yinhai.ec.common.util.ZtreeJsonUtils;
import com.yinhai.ec.system.service.OrgManagerService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TANQINGPING
 * @version 1.0
 * @package com.yinhai.ec.base.system.controller
 * <p>Title: OrgManagerController.java</p>
 * <p>Description: 部门管理</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 陈瓜瓜</p>
 * date 2016-4-25 下午1:08:42
 */
@Controller
@RequestMapping("system/org/orgManager")
public class OrgManagerController extends BaseController {
    @Autowired
    private OrgManagerService service;

    @RequestMapping("/default")
    public String orgList(ModelMap map) throws Exception {
        List<OrgDomain> list = service.queryOrgList();
        map.put("list", ZtreeJsonUtils.toZtreeJsonWithNamepath(list));
        return "/system/orgManager.jsp";
    }

    @RequestMapping("/saveOrg")
    @ResponseBody
    public ResultBean saveOrg(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean bean = new ResultBean();
        if (pageParam.isEmpty()) {
            bean.setError(true);
            bean.setError_msg("保存失败:未获取到需要操作的部门信息");
        } else {
            OrgDomain orgDomain = new OrgDomain();
            Object orgId = pageParam.get("orgId");
            Object orgName = pageParam.get("orgName");
            Object parentId = pageParam.get("pId");
            if (orgId != null && (!orgId.toString().trim().equals(""))) {
                orgDomain.setOrgId(Integer.parseInt(orgId.toString()));
            }
            if (orgName != null) {
                orgDomain.setOrgName(orgName.toString());
            }
            if (parentId != null && (!parentId.toString().trim().equals(""))) {
                orgDomain.setParentOrgId(Integer.parseInt(parentId.toString()));
            }
            service.saveOrg(orgDomain, getUserInfo(), bean);
            service.updateLeaf(bean);
        }
        return bean;
    }

    @RequestMapping("/deleteOrg")
    @ResponseBody
    public ResultBean deleteOrg(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean bean = new ResultBean();
        if (pageParam.isEmpty()) {
            bean.setError(true);
            bean.setError_msg("删除失败:未获取到需要操作的部门信息");
        } else {
            service.deleteOrg(Integer.parseInt(pageParam.get("orgId").toString()), bean);
            service.updateLeaf(bean);
        }
        return bean;
    }

}
 