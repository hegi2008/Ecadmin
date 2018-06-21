package com.yinhai.hyman.weixin.message;
/**
 * 提交至微信的图文消息素材
 * ====================================================================
 *  
 * --------------------------------------------------------------------
 * @author Nottyjay
 * @version 1.0.beta
 * ====================================================================
 */
public class MpNewsMsg extends BaseMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mediaId;

    public MpNewsMsg() {
    }

    public MpNewsMsg(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
