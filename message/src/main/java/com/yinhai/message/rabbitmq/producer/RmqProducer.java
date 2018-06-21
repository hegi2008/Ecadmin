package com.yinhai.message.rabbitmq.producer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yinhai.message.rabbitmq.EndPoint;

/**
* @package com.yinhai.message.rabbitmq.producer
* <p>Title: RmqProducer.java</p>
* <p>RabbitMQ 消息Producer 用于发送消息 ...</p>
* @author cjh
* @date 2016年11月15日 下午2:24:08
 */
public class RmqProducer extends EndPoint{
	private static final Logger LOGGER = LoggerFactory.getLogger(RmqProducer.class);

	public RmqProducer() {
	}
	
	// 消费者是无法订阅或者获取不存在的MessageQueue中信息
	// 消息被Exchange接受以后，如果没有匹配的Queue，则会被丢弃
	public void sendMessage(final Object message) {
		if(!checkAndWaitConnection()){
			return;
		}
		// 每次都获取Channel 保证一个线程对应自己的Channel
		Channel channel = getChannel();
		try {
			channel.basicPublish(exchange, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, getSerialBytes(message));
			LOGGER.info("成功发送消息exchange="+exchange+",routingKey="+routingKey+",message="+message.toString());
		} catch (IOException e) {
			LOGGER.error("发布消息失败,{}", e);
		}
	}
}
