package com.yinhai.ec.demo.websocket.interceptor;  

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.UserDomain;
  
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
	private Logger log = LoggerFactory.getLogger(HandshakeInterceptor.class);
	
	@Override  
    public boolean beforeHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Map<String, Object> attributes) throws Exception {  
		log.info("握手WebSocket");
		//super.beforeHandshake(request, response, wsHandler, attributes);
		if (request instanceof ServletServerHttpRequest) {
	        Session session = SecurityUtils.getSubject().getSession(false);
	        UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
	        attributes.put("user_id", user.getUserId());
	        attributes.put("http.session.id", session.getId());
	    }
        return true;
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Exception ex) {  
    	super.afterHandshake(request, response, wsHandler, ex);
    	log.info("握手结束");
    }
}
 