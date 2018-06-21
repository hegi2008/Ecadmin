package com.yinhai.ec.demo.websocket.configurer;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.yinhai.ec.demo.websocket.handler.MyTextHandler;
import com.yinhai.ec.demo.websocket.interceptor.HandshakeInterceptor;
 
@Configuration
@EnableWebSocket
public class WebSocketConfigurer implements org.springframework.web.socket.config.annotation.WebSocketConfigurer{
	private Logger log = LoggerFactory.getLogger(WebSocketConfigurer.class);

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MyTextHandler(), "/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
		registry.addHandler(new MyTextHandler(), "/sockjs/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
		log.info("注册spring WebSocket服务");
	}
	
	@Bean
    public WebSocketHandler myTextHandler(){
        return new MyTextHandler();
    }
	
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}
 