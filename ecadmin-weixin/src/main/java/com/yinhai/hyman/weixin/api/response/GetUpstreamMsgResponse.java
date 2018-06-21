package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.UpstreamMsg;

/**
 * @author peiyu
 */
public class GetUpstreamMsgResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UpstreamMsg> list;

    public List<UpstreamMsg> getList() {
        return list;
    }

    public void setList(List<UpstreamMsg> list) {
        this.list = list;
    }
}
