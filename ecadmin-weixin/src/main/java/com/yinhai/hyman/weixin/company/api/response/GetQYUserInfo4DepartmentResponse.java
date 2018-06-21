package com.yinhai.hyman.weixin.company.api.response;/**
 * Created by Nottyjay on 2015/6/11.
 */

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;
import com.yinhai.hyman.weixin.company.api.entity.QYUser;

/**
 * ====================================================================
 *  
 * --------------------------------------------------------------------
 *
 * @author Nottyjay
 * @version 1.0.beta
 *          ====================================================================
 */
public class GetQYUserInfo4DepartmentResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "userlist")
    public List<QYUser> userList;

    public List<QYUser> getUserList() {
        return userList;
    }

    public void setUserList(List<QYUser> userList) {
        this.userList = userList;
    }
}
