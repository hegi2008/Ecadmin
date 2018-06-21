package com.yinhai.ec.demo.websocket.service;

import java.util.List;
import java.util.Map;

import com.yinhai.ec.base.service.BaseService;

@SuppressWarnings("rawtypes")
public interface ChatService extends BaseService{
	/**
	 * 查询用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List queryUserList(Map<String,Object> param) throws Exception;
}
