package com.yinhai.hyman.weixin.company.api.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;
import com.yinhai.hyman.weixin.company.api.entity.QYDepartment;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class GetDepartmentListResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "department")
    private List<QYDepartment> departments;

    public List<QYDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(List<QYDepartment> departments) {
        this.departments = departments;
    }
}
