package com.yinhai.shh.account.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String yad901;

    private String yad961;

    private String des;

    private Date create_time;
    
    private Date update_time;

    public String getYad901() {
        return yad901;
    }

    public void setYad901(String yad901) {
        this.yad901 = yad901;
    }

    public String getYad961() {
        return yad961;
    }

    public void setYad961(String yad961) {
        this.yad961 = yad961;
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