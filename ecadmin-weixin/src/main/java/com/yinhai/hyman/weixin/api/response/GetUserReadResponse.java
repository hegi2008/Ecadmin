package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UserRead;

/**
 * @author peiyu
 */
public class GetUserReadResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserRead> list;

    public List<UserRead> getList() {
        return list;
    }

    public void setList(List<UserRead> list) {
        this.list = list;
    }
}
