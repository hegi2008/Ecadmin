package com.yinhai.ec.demo.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yinhai.ec.weixin.Servlet.WeixinControllerSupport;
import com.yinhai.hyman.weixin.api.config.ApiConfig;
import com.yinhai.hyman.weixin.message.BaseMsg;
import com.yinhai.hyman.weixin.message.TextMsg;
import com.yinhai.hyman.weixin.message.req.BaseEvent;
import com.yinhai.hyman.weixin.message.req.LocationEvent;
import com.yinhai.hyman.weixin.message.req.LocationReqMsg;
import com.yinhai.hyman.weixin.message.req.TemplateMsgEvent;

//@Controller
@RequestMapping("/weixin")
public class WeiXinEntranceController extends WeixinControllerSupport{
	private Logger log = LoggerFactory.getLogger(WeiXinEntranceController.class);
	
	@Value("#{wxconfig['wx.token']}")
	private String token;
	
	@SuppressWarnings("unused")
	@Autowired
	private ApiConfig config;
	
	@Override
	protected String getToken() {
		return token;
	}
	
	@Override
	protected String getAppId() {
		return super.getAppId();
	}

	@Override
	protected BaseMsg handleLocationMsg(LocationReqMsg msg) {
		log.debug("接收到地理位置消息,{}", msg.toString());
		return super.handleLocationMsg(msg);
	}
	
	@Override
	protected BaseMsg handleLocationEvent(LocationEvent event) {
		log.debug("接收到地理位置事件,{}", event.toString());
		return super.handleLocationEvent(event);
	}
	
	@Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
		return new TextMsg("感谢您的关注!");
	}
	
	@Override
	protected BaseMsg handleTemplateMsgEvent(TemplateMsgEvent event) {
		log.debug("接收到发送模板消息回调事件,{}", event.toString());
		return super.handleTemplateMsgEvent(event);
	}
	
}
