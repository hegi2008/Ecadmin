package com.yinhai.hyman.weixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class UploadMaterialResponse extends BaseResponse  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "media_id")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
