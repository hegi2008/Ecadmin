package com.yinhai.message.rabbitmq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.yinhai.message.rabbitmq.connection.ConnectionManager;

/**
* @package com.yinhai.message.rabbitmq
* <p>Title: EndPoint.java</p>
* <p>RabbitMQ 生产者和消费者的基类</p>
* @author cjh
* @date 2016年11月15日 下午4:57:45
 */
public class EndPoint {
	/**
	 * 路由key 实际上就是队列名称
	 */
	protected String routingKey;
	/**
	 * 配置文件指定的 exchange,默认 ""
	 */
	protected String exchange;
	/**
	 * 交换器(exchange)类型
	 */
	protected String exchangeType;
	protected final RmqConfig config;
	protected final ConnectionManager connectionManager = ConnectionManager.getInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(EndPoint.class);
	
	public EndPoint() {
		if(!connectionManager.isInit()){
			throw new RabbitmqConnException("singleton ConnectionManager has not been inited...");
		}
		config = connectionManager.getConfig();
		initEndPoint(config);
	}
	
	private void initEndPoint(RmqConfig config) {
		this.exchange = config.getExchange() == null ?"":config.getExchange();
		this.exchangeType=config.getExchangeType() == null?RmqConfig.ExchangeType.DIRECT.getValue():config.getExchangeType();
		this.routingKey = config.getQueue();
		
		Channel channel = null;
		try {
			channel = connectionManager.createChannel();
			channel.queueDeclare(routingKey, config.isDurable_queue(), config.isExclusive_queue(), config.isAutoDelete_queue(), null);
			// 声明一个队列,如果队列已经存在,RabbitMQ不会做任何事情,会返回建立成功
			// 如果指定了exchange
			if(!"".equals(this.exchange)){
				updateExchange(exchange, exchangeType, routingKey, channel);
			}
		} catch (IOException e) {
			LOGGER.error("初始化EndPoint失败,{}",e);
		} finally {
			if (channel != null && channel.isOpen()) {
				connectionManager.removeChannel(Thread.currentThread());
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	protected boolean checkAndWaitConnection() {
		while (!connectionManager.getConnection().isOpen()) {
			if(connectionManager.isBroken()){
				// 连接断开,并且尝试恢复失败
				// 不在继续等待连接了
				return false;
			}
			LOGGER.warn("connection 断开连接,尝试重新获取channel");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	protected Channel getChannel() {
		return connectionManager.createChannel();
	}
	
	/**
	  * exchange处理 三种exchange,需灵活使用,达到不同的效果
	  * ①direct 绑定路由建(队列名),需要将一个队列绑定到该交换机上,要求消息与一个特定的路由键完全匹配
	  * ②fanout 不处理路由键(队列名),只需要将队列绑定到交换机上,一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上,很想子网广播.fanout交换机转发消息是最快的.
	  * ③topic  将路由键(队列)和某模式进行匹配,此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”
	  * @return void 
	  * @author cjh
	 * @throws IOException 
	 */
	protected void updateExchange(String exchange, String exchangeType, String routingKey, Channel channel) throws IOException {
		if(exchange == null || exchangeType == null || "".equals(exchangeType)){
			throw new NullPointerException("update exchange failed:exchange or exchangeType cannot be null");
		}
		if(routingKey == null || "".equals(routingKey)){
			throw new NullPointerException("update exchange failed:routingKey cannot be null");
		}
		// 声明一个 exchange
		channel.exchangeDeclare(exchange, exchangeType, config.isDurable_exchange(), config.isAutoDelete_exchange(), null);
		// 将队列绑定到交换器上
		channel.queueBind(routingKey, exchange, routingKey);
	}
	
	public byte[] getSerialBytes(Object object) {
		byte[] res = new byte[0];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			res = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
