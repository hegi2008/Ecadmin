package com.yinhai.hyman.weixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 发送模版消息响应
 */
public class SendTemplateResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 消息id
     */
    @JSONField(name = "msgid")
    private String msgid;

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
