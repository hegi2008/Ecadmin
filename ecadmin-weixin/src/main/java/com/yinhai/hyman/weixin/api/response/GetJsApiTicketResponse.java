package com.yinhai.hyman.weixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author daxiaoming
 */
public class GetJsApiTicketResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ticket;

    @JSONField(name = "expires_in")
    private Integer expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
