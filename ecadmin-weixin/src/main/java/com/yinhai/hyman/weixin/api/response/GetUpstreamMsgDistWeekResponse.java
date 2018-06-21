package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UpstreamMsgDistWeek;

/**
 * @author peiyu
 */
public class GetUpstreamMsgDistWeekResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UpstreamMsgDistWeek> list;

    public List<UpstreamMsgDistWeek> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgDistWeek> list) {
        this.list = list;
    }
}
