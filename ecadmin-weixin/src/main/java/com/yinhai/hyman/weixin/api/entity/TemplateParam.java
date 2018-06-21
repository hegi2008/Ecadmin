package com.yinhai.hyman.weixin.api.entity;

/**
 * 模版参数
 */
public class TemplateParam extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 值
     */
    private String value;
    /**
     * 颜色
     */
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
