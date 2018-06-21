package com.yinhai.message.rabbitmq.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.yinhai.message.rabbitmq.EndPoint;

/**
 * 
* @package com.yinhai.message.rabbitmq.consumer
* <p>Title: LongConnectionConsumer.java</p>
* <p>基于长连接的Consumer的实现.(订阅型)</p>
* <p>连接断开时,会尝试重新建立连接.但是并不会自动重新发起订阅,需要调用的时候手动重新发起订阅.</p>
* @author cjh
* @date 2017年2月8日 下午3:12:05
 */
public class LongConnectionConsumer extends EndPoint implements Runnable, Consumer{
	private static final Logger LOGGER = LoggerFactory.getLogger(LongConnectionConsumer.class);
	/**mq队列是否默认消息已消费 false时需要调用channel.basicAck 才表示已消费,队列将移除该条消息**/
	private volatile boolean autoAck = false;
	/**停止订阅标志**/
	private volatile boolean stopConsume = false;
	/**是否取消订阅**/
	private volatile boolean cancelConsume = false;
	/**成功订阅的 consumerTag**/
	private volatile String consumerTag = null;
	/**绑定Consumer的Channel**/
	public Channel channel = null;

	public LongConnectionConsumer() {
	}
	
	public LongConnectionConsumer(boolean autoAck) {
		this.autoAck = autoAck;
	}
	
	@Override
	public void run() {
		// 实现 Runnable,监听队列消息并处理
		// Subscribe订阅方式
		channel = getChannel();
		try {
			channel.basicConsume(routingKey, autoAck, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Called when the consumer is registered by a call to any of the
     * {@link Channel#basicConsume} methods.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     */
	@Override
	public void handleConsumeOk(String consumerTag) {
		cancelConsume = false;
		stopConsume = false;
		this.consumerTag = consumerTag;
		LOGGER.debug("Consumer "+consumerTag+" registered");
	}

	/**
     * Called when the consumer is cancelled by a call to {@link Channel#basicCancel}.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     */
	@Override
	public void handleCancelOk(String consumerTag) {
		cancelConsume = true;
		stopConsume = true;
		this.consumerTag = null;
		LOGGER.warn("Consumer "+consumerTag+" has been cancelled");
	}

	/**
     * Called when the consumer is cancelled for reasons <i>other than</i> by a call to
     * {@link Channel#basicCancel}. For example, the queue has been deleted.
     * See {@link #handleCancelOk} for notification of consumer
     * cancellation due to {@link Channel#basicCancel}.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     * @throws IOException
     */
	@Override
	public void handleCancel(String consumerTag) throws IOException {
		cancelConsume = true;
		stopConsume = true;
		this.consumerTag = null;
		LOGGER.warn("Consumer "+consumerTag+" has been cancelled: may be the queue has been deleted");
	}

	/**
     * Called when a <code><b>basic.deliver</b></code> is received for this consumer.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     * @param envelope packaging data for the message
     * @param properties content header data for the message
     * @param body the message body (opaque, client-specific byte array)
     * @throws IOException if the consumer encounters an I/O error while processing the message
     * @see Envelope
     */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
		handleMessage(envelope, properties, body);
	}

	/**
     * Called when either the channel or the underlying connection has been shut down.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     * @param sig a {@link ShutdownSignalException} indicating the reason for the shut down
     */
	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
		stopConsume = true;
		this.consumerTag = null;
		LOGGER.warn("Consumer "+consumerTag+": either the channel or the underlying connection has been shut down");
	}

	/**
     * Called when a <code><b>basic.recover-ok</b></code> is received
     * in reply to a <code><b>basic.recover</b></code>. All messages
     * received before this is invoked that haven't been <i>ack</i>'ed will be
     * re-delivered. All messages received afterwards won't be.
     * @param consumerTag the <i>consumer tag</i> associated with the consumer
     */
	@Override
	public void handleRecoverOk(String consumerTag) {
		stopConsume = false;
		cancelConsume = false;
		this.consumerTag = consumerTag;
	}
	
	private Object handleMessage(Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
		long deliveryTag = envelope.getDeliveryTag();
		String contentType = properties.getContentType();
		Object obj = toObject(body);
		LOGGER.debug("接收到消息: deliveryTag="+deliveryTag+" 消息内容="+obj.toString()+" contentType="+contentType);
		if(!isAutoAck()){
			channel.basicAck(deliveryTag, false);
		}
		return obj;
	}

	public boolean isAutoAck() {
		return autoAck;
	}

	public void setAutoAck(boolean autoAck) {
		this.autoAck = autoAck;
	}

	public boolean isStopConsume() {
		return stopConsume;
	}

	public String getConsumerTag() {
		return consumerTag;
	}

	public void setConsumerTag(String consumerTag) {
		this.consumerTag = consumerTag;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public boolean isCancelConsume() {
		return cancelConsume;
	}
	public void setCancelConsume(boolean cancelConsume) {
		this.cancelConsume = cancelConsume;
	}
	public void setStopConsume(boolean stopConsume) {
		this.stopConsume = stopConsume;
	}
}
