package com.yinhai.xtpt.cdsi;

import java.io.Serializable;

public class CdsiInvokeRequest implements Serializable {
	private static final long serialVersionUID = 8333428637777957006L;
	/** 接入系统标识 **/
	private String yad900;
	/** 流水号 **/
	private String yad901;
	/** 交易服务编号 **/
	private String yad902;
    /** 随机码 **/
	private String yad904;
	/** 签名 **/
	private String yad905;
	/** 参数 **/
	private Object yad906;
    /** 返回数据类型 **/
	private String resultType = "json";

	public CdsiInvokeRequest() {

	}

	public String getYad900() {
		return yad900;
	}

	public void setYad900(String yad900) {
		this.yad900 = yad900;
	}

	public String getYad901() {
		return yad901;
	}

	public void setYad901(String yad901) {
		this.yad901 = yad901;
	}

	public String getYad902() {
		return yad902;
	}

	public void setYad902(String yad902) {
		this.yad902 = yad902;
	}

	public String getYad904() {
		return yad904;
	}

	public void setYad904(String yad904) {
		this.yad904 = yad904;
	}

	public String getYad905() {
		return yad905;
	}

	public void setYad905(String yad905) {
		this.yad905 = yad905;
	}

	public Object getYad906() {
		return yad906;
	}

	public void setYad906(Object yad906) {
		this.yad906 = yad906;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

}
