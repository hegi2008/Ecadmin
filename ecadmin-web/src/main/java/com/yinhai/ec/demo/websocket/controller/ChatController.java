package com.yinhai.ec.demo.websocket.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.session.dao.HYBaseSessionDao;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.demo.websocket.handler.MyTextHandler;
//@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController{
	
	@Resource
	private MyTextHandler myTextHandler;
	@Autowired
	private HYBaseSessionDao sessionDao;
	@RequestMapping("/online")
	@ResponseBody
	public Map<String,Object> getOnlineUsers() throws Exception{
		ConcurrentMap<String, WebSocketSession> users = myTextHandler.getUserMap();
		Collection<Session> sessions = sessionDao.getActiveSessions();
		//在线好友列表
		List<Map<String, Object>> onlineUsers = new ArrayList<Map<String, Object>>();
		//当前用户信息
		Map<String,Object> me = new HashMap<String,Object>();
		Map<String,String> ids = new HashMap<String,String>();
		String id  = String.valueOf(getUserInfo().getUserId().intValue());
		for (Session session : sessions) {
			UserDomain domain = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			if (domain == null) {
				session.stop();
				continue;
			}
			String userid = String.valueOf(domain.getUserId().intValue());
			if(!id.equals(userid) && !ids.containsKey(userid)&& users.get(userid) != null && users.get(userid).isOpen()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", domain.getUserId());
				map.put("face", domain.getUserHeaderPic());
				map.put("name", domain.getUserName());
				ids.put(userid, userid);
				onlineUsers.add(map);
				map = null;
			}
			if(id.equals(userid)){
				me.put("id", domain.getUserId());
				me.put("face", domain.getUserHeaderPic());
				me.put("name", domain.getUserName());
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("error", false);
		result.put("data",onlineUsers);
		result.put("me", me);
		ids.clear();
		return result;
	}
	@RequestMapping("/user")
	@ResponseBody
	public Map<String,Object> getUser(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Collection<Session> sessions = sessionDao.getActiveSessions();
		Map<String,Object> map = new HashMap<String,Object>();
		for (Session session : sessions) {
			UserDomain domain = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
			if (domain == null) {
				session.stop();
				continue;
			}
			String userid = String.valueOf(domain.getUserId().intValue());
			if(id.equals(userid)){
				map.put("id", domain.getUserId());
				map.put("face", domain.getUserHeaderPic());
				map.put("name", domain.getUserName());
				break;
			}
		}
		return map;
	}
}
