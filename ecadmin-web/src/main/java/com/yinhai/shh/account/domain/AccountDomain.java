package com.yinhai.shh.account.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String out_platform_id;

    private String channel;

    private String des;

    private Date create_time;
    
    private Date update_time;

    public String getOut_platform_id() {
        return out_platform_id;
    }

    public void setOut_platform_id(String out_platform_id) {
        this.out_platform_id = out_platform_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}