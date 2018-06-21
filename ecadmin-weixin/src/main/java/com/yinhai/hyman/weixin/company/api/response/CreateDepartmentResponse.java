package com.yinhai.hyman.weixin.company.api.response;

import com.yinhai.hyman.weixin.api.response.BaseResponse;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class CreateDepartmentResponse extends BaseResponse {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreateDepartmentResponse{" +
                "id=" + id +
                '}';
    }
}
