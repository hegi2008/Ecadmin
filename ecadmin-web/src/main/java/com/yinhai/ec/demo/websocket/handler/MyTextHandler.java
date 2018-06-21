package com.yinhai.ec.demo.websocket.handler;  

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
  
@SuppressWarnings("rawtypes")
public class MyTextHandler extends TextWebSocketHandler {
	private static Logger log = LoggerFactory.getLogger(MyTextHandler.class);
	private static final ConcurrentMap<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String u_id = null;
		if(session.getAttributes() != null && (u_id=session.getAttributes().get("user_id").toString()) != null){
			users.put(u_id, session);
			Map<String,Object> msg = new HashMap<String,Object>();
			msg.put("id", u_id);
			msg.put("online", true);
			sendMsgToUsers(msg);
		}
		log.info("WebSocket连接已经建立");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage textmessage) throws Exception {
		super.handleTextMessage(session, textmessage);
		String msg = textmessage.getPayload();
		Map map = JSON.parseObject(msg, Map.class);
		String to = map.get("to").toString();
		if(users.get(to) !=null && users.get(to).isOpen()){
			users.get(to).sendMessage(new TextMessage(msg));
		}
		//session.sendMessage(new TextMessage(msg));
		/*Map<String, Object> map = JSONObject.parseObject(msg);
		if(map.get("userid") != null && StringUtils.isEmpty(map.get("userid").toString())){
			users.put(map.get("userid").toString(), session);
		}*/
	}
	

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable arg1)
			throws Exception {
		if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        arg1.printStackTrace();
        String u_id = webSocketSession.getAttributes().get("user_id").toString();
        users.remove(u_id);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status)
			throws Exception {
		log.info("关闭连接" + status.toString());
		String u_id = webSocketSession.getAttributes().get("user_id").toString();
        users.remove(u_id);
        Map<String,Object> msg = new HashMap<String,Object>();
		msg.put("id", u_id);
		msg.put("online", false);
		sendMsgToUsers(msg);
	}
	
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	public static void sendMsgToUser(String session_id,String msg){
		WebSocketSession session = users.get(session_id);
		if(session != null){
			try {
				session.sendMessage(new TextMessage(msg));
			} catch (IOException e) {
				log.error("发送websocket消息给用户:"+session_id+" 失败",e);
			}
		}
	}
	/**
	 * 给所有人发送消息
	 * @param msg
	 * @throws IOException 
	 */
	public void sendMsgToUsers(Map<String,Object> msg) throws IOException{
		String uid = msg.get("id").toString(); 
		String message = JSON.toJSONString(msg);
		for(String id:users.keySet()){
			WebSocketSession session = (WebSocketSession)users.get(id);
			if(!uid.equals(id) && session!=null && session.isOpen()){
				session.sendMessage(new TextMessage(message));
			}
		}
	}
	
	public ConcurrentMap<String, WebSocketSession> getUserMap(){
		return users;
	}
}
 