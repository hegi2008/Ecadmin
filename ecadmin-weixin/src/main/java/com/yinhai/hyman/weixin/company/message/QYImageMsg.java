package com.yinhai.hyman.weixin.company.message;/**
 * Created by Nottyjay on 2015/6/12.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ====================================================================
 *  
 * --------------------------------------------------------------------
 *
 * @author Nottyjay
 * @version 1.0.beta
 * ====================================================================
 */
public class QYImageMsg extends QYBaseMsg{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "image")
    private Image image;

    public QYImageMsg() { this.setMsgType("image"); }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setMediaId(String mediaId) { this.image = new Image(mediaId); }

    public class Image{
        @JSONField(name = "media_id")
        private String mediaId;

        public Image(String mediaId) {this.mediaId = mediaId;}

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }
}
