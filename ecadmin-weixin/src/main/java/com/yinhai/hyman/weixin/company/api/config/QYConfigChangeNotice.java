package com.yinhai.hyman.weixin.company.api.config;

import java.util.Date;

import com.yinhai.hyman.weixin.api.config.ChangeType;
import com.yinhai.hyman.weixin.api.entity.BaseModel;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class QYConfigChangeNotice extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date noticeTime;
    private String corpID;
    private ChangeType type;
    private String value;

    public QYConfigChangeNotice() {
        this.noticeTime = new Date();
    }

    public QYConfigChangeNotice(String corpID, ChangeType type, String value) {
        this();
        this.corpID = corpID;
        this.type = type;
        this.value = value;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getCorpID() {
        return corpID;
    }

    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    public ChangeType getType() {
        return type;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "QYConfigChangeNotice{" +
                "noticeTime" + noticeTime +
                ", corpid='" + corpID + "'" +
                ", type=" + type +
                ", value='" + value + "'" +
                "}";
    }
}
