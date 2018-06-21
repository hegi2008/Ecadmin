package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UpstreamMsgWeek;

/**
 * @author peiyu
 */
public class GetUpstreamMsgWeekResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UpstreamMsgWeek> list;

    public List<UpstreamMsgWeek> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgWeek> list) {
        this.list = list;
    }
}
