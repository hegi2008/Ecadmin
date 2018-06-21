package com.yinhai.hyman.weixin.company.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.response.BaseResponse;

/**
 *  Response -- 增加标签成员
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  @since 1.3.6
 *  ====================================================================
 */
public class AddTagUsersResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String invalidlist;
    private List<Integer> invalidparty;

    public String getInvalidlist() {
        return invalidlist;
    }

    public void setInvalidlist(String invalidlist) {
        this.invalidlist = invalidlist;
    }

    public List<Integer> getInvalidparty() {
        return invalidparty;
    }

    public void setInvalidparty(List<Integer> invalidparty) {
        this.invalidparty = invalidparty;
    }
}
