package com.yinhai.hyman.weixin.message;

import com.yinhai.hyman.weixin.message.util.MessageBuilder;

/**
 * @author peiyu
 */
public class VoiceMsg extends BaseMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mediaId;

    public VoiceMsg(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespType.VOICE);
        mb.append("<Voice>\n");
        mb.addData("MediaId", mediaId);
        mb.append("</Voice>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

}
