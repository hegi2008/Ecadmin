package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class AppCodeDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codeString;

    private String codeName;

    private String codeValue;

    private String codeValueName;

    private Integer codeType;

    private Integer codeCreateer;

    private Date codeCreateTime;

    private Integer codeStatus;

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString == null ? null : codeString.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getCodeValueName() {
        return codeValueName;
    }

    public void setCodeValueName(String codeValueName) {
        this.codeValueName = codeValueName == null ? null : codeValueName.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getCodeCreateer() {
        return codeCreateer;
    }

    public void setCodeCreateer(Integer codeCreateer) {
        this.codeCreateer = codeCreateer;
    }

    public Date getCodeCreateTime() {
        return codeCreateTime;
    }

    public void setCodeCreateTime(Date codeCreateTime) {
        this.codeCreateTime = codeCreateTime;
    }

    public Integer getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Integer codeStatus) {
        this.codeStatus = codeStatus;
    }
}