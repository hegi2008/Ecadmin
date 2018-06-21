package com.yinhai.hyman.weixin.message;

import com.yinhai.hyman.weixin.message.util.MessageBuilder;

public class CustomMsg extends BaseMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String kfAccount;

    public CustomMsg(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespType.KF);
        mb.append("<TransInfo>\n");
        mb.addData("KfAccount", kfAccount);
        mb.append("</TransInfo>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

}
