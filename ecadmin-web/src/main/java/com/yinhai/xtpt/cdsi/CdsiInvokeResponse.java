package com.yinhai.xtpt.cdsi;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CdsiInvokeResponse implements Serializable {
	private static final long serialVersionUID = 6586402036379062635L;
	/** 随机码 **/
	@JSONField(name="yad904")
	private String yad904;
	/** 签名 **/
	@JSONField(name="yad905")
	private String yad905;
	/** invoke是否成功执行 **/
	@JSONField(name="yad907")
    private String yad907;
    /** INVOKE_SUCCESS_MSG **/
	@JSONField(name="yad908")
    private String yad908;
    /** 返回结果 就算invoke成功执行,服务也有可能调用出错 **/
	@JSONField(name="yad909")
    private Object yad909;
    public static final String INVOKE_SUCCESS = "1";
    public static final String INVOKE_FAIL = "-1";
    public static final String INVOKE_SUCCESS_MSG = "成功";
    public CdsiInvokeResponse() {
		
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
	public String getYad907() {
		return yad907;
	}
	public void setYad907(String yad907) {
		this.yad907 = yad907;
	}
	public String getYad908() {
		return yad908;
	}
	public void setYad908(String yad908) {
		this.yad908 = yad908;
	}
	public Object getYad909() {
		return yad909;
	}
	public void setYad909(Object yad909) {
		this.yad909 = yad909;
	}
}
