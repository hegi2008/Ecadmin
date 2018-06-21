package com.yinhai.message.rabbitmq.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import com.yinhai.message.rabbitmq.EndPoint;

/**
* @package com.yinhai.message.rabbitmq.consumer
* <p>Title: OncePerConsumer.java</p>
* <p>每次主动从队列中取一条消息(非订阅模式)</p>
* @author cjh
* @date 2017年3月1日 下午2:56:24
 */
public class OncePerConsumer extends EndPoint{
	private static final Logger LOGGER = LoggerFactory.getLogger(OncePerConsumer.class);
	/**mq队列是否默认消息已消费 false时需要调用channel.basicAck 才表示已消费,队列将移除该条消息**/
	private volatile boolean autoAck = false;
	/**绑定Consumer的Channel**/
	public Channel channel = null;
	
	public OncePerConsumer() {
	}
	
	/**
	 * 获取一条消息,需要判空
	 * <p>如果连接断开,会阻塞,等连接重新建立,直到ConnectionManager destroy </p>
	  * @return Object 
	  * @author cjh
	 */
	public GetResponse getMessage(String queue) {
		GetResponse getResponse = null;
		if(!checkAndWaitConnection()){
			return getResponse;
		}
		this.channel = getChannel();
		try {
			getResponse = channel.basicGet(queue, autoAck);
			if (getResponse != null && LOGGER.isInfoEnabled()) {
				long deliverTag = getResponse.getEnvelope().getDeliveryTag();
				Object object = toObject(getResponse.getBody());
				LOGGER.info("接收到消息: deliveryTag="+deliverTag+" 消息内容="+ object+" contentType="+getResponse.getProps().getContentType());
			}
		} catch (IOException e) {
			LOGGER.error("获取消息失败:{}", e.getMessage());
		}
		return getResponse;
	}
	
	/**
	 * 获取一条消息
	 * <p>如果连接断开,会阻塞,等连接重新建立,直到ConnectionManager destroy </p>
	  * @return Object 
	  * @author cjh
	 */
	public GetResponse getMessage() {
		return getMessage(routingKey);
	}

	/**
	 * 确认收到消息
	 * <p>如果在消息处理期间,连接断开了 将导致消息确认不成功,程序退出</p>
	 * <p>建议:拿到消息后,判空后立即进行确认.</p>
	  * @return void 
	  * @author cjh
	 */
	public void ackMessage(long deliveryTag) {
		try {
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			LOGGER.error("消息确认失败:{}", e.getMessage());
		}
	}
	
	public boolean isAutoAck() {
		return autoAck;
	}

	public void setAutoAck(boolean autoAck) {
		this.autoAck = autoAck;
	}

	public Channel getChannel() {
		if(channel == null || !channel.isOpen()){
			channel = super.getChannel();
		}
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
