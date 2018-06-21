package com.yinhai.hyman.weixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author peiyu
 */
public class QrcodeResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  ticket;
    @JSONField(name = "expire_seconds")
    private Integer expireSeconds;
    private String  url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
