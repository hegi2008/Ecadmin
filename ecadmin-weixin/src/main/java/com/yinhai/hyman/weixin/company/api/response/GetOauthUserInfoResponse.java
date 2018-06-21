package com.yinhai.hyman.weixin.company.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;

/**
 * Response -- 从Oauth中获取的用户信息
 * ====================================================================
 *  
 * --------------------------------------------------------------------
 * @author Nottyjay
 * @version 1.0.beta
 * @since 1.3.6
 * ====================================================================
 */
public class GetOauthUserInfoResponse extends BaseResponse{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "UserId")
    private String userid;
    @JSONField(name = "OpenId")
    private String openid;
    @JSONField(name = "DeviceId")
    private String deviceid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
