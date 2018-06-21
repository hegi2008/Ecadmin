package com.yinhai.hyman.weixin.api.entity;

/**
 * @author Nottyjay
 */
public class UserInfo extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openid;
    private String lang="zh_CN";
    public UserInfo(){

    }
    public UserInfo(String openid){
        this.openid=openid;
    }
    public UserInfo(String openid, String lang){
        this.openid=openid;
        this.lang=lang;
    }
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}