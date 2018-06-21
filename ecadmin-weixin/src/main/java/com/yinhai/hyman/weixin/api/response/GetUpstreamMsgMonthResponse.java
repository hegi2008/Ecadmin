package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UpstreamMsgMonth;

/**
 * @author peiyu
 */
public class GetUpstreamMsgMonthResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UpstreamMsgMonth> list;

    public List<UpstreamMsgMonth> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgMonth> list) {
        this.list = list;
    }
}
