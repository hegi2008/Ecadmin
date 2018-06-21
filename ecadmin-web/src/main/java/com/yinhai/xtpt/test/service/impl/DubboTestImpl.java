package com.yinhai.xtpt.test.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.xtpt.test.service.DubboTest;

public class DubboTestImpl implements DubboTest{

	@Override
	public String sayHello(String name, String pwd) {
		System.out.println("收到名称:========"+name);
		System.out.println("收到密码:========"+pwd);
		Map<String, String> returnMap = new HashMap<String,String>();
		returnMap.put("name", "好久回");
		returnMap.put("pwd", "sss");
		returnMap.put("name1", "圣诞节是刻录机");
		return JSONObject.toJSONString(returnMap);
	}

	@Override
	public String sayHello(String name) {
		System.out.println("收到名称:========"+name);
		Map<String, String> returnMap = new HashMap<String,String>();
		returnMap.put("name", "好久回");
		returnMap.put("pwd", "sss");
		returnMap.put("name1", "圣诞节是刻录机");
		return JSONObject.toJSONString(returnMap);
	}

}
