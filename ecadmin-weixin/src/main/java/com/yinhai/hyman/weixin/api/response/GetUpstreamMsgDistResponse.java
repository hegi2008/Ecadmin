package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UpstreamMsgDist;

/**
 * @author peiyu
 */
public class GetUpstreamMsgDistResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UpstreamMsgDist> list;

    public List<UpstreamMsgDist> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgDist> list) {
        this.list = list;
    }
}
