package com.yinhai.ec.base.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SystemPropertiesManager {
	
	@Value("${system.isNeedAuthCode:true}")
	private boolean isNeedAuthCode;
	@Value("${system.theme:hplus}")
	private String systemTheme;
	
	/**
	 *  isNeedAuthCode
	  * @return boolean 
	  * @author cjh
	 */
	public boolean isNeedAuthCode() {
		return isNeedAuthCode;
	}

	/**
	 * 获取系统主题
	 * @return
     */
	public String getSystemTheme() {
		return systemTheme;
	}
}
