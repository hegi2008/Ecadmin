package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UserShareHour;

/**
 * @author peiyu
 */
public class GetUserShareHourResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserShareHour> list;

    public List<UserShareHour> getList() {
        return list;
    }

    public void setList(List<UserShareHour> list) {
        this.list = list;
    }
}
