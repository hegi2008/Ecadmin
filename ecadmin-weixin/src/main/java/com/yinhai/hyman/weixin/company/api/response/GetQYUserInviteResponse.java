package com.yinhai.hyman.weixin.company.api.response;/**
 * Created by Nottyjay on 2015/6/11.
 */

import com.yinhai.hyman.weixin.api.response.BaseResponse;

/**
 * ====================================================================
 *  
 * --------------------------------------------------------------------
 *
 * @author Nottyjay
 * @version 1.0.beta
 *          ====================================================================
 */
public class GetQYUserInviteResponse extends BaseResponse {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
