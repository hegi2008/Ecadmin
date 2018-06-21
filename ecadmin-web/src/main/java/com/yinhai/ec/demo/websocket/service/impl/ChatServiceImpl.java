package com.yinhai.ec.demo.websocket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.demo.websocket.service.ChatService;
@Service
@SuppressWarnings("rawtypes")
public class ChatServiceImpl extends BaseServiceImpl implements ChatService{

	
	@Override
	public List queryUserList(Map<String, Object> param) throws Exception {
		return sqlSession.selectList("chat.queryUserList", param);
	}

	
}
