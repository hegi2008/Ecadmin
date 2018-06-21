package com.yinhai.message.rabbitmq.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.yinhai.message.rabbitmq.RabbitmqConnException;
import com.yinhai.message.rabbitmq.RmqConfig;

/**
* @package com.yinhai.message.rabbitmq.connection
* <p>Title: ConnectionManager.java</p>
* <p>RabbitMQ 连接管理 发送数据是,一个线程对应一个Channel</p>
* @author cjh
* @date 2016年11月15日 下午2:05:44
 */
public class ConnectionManager {
	private static final String DEFAULT_RESOURCE = "rabbitmq.properties";
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
	private final ConnectionFactory factory = new ConnectionFactory();
	// 每个线程对应自己的channel 针对发送消息
	private Map<Thread, Channel> channels = new ConcurrentHashMap<Thread, Channel>();
	private RmqConfig config;
	private Connection connection;
	private volatile boolean isInit = false;
	private ConnectionManager(){}
	// 断开连接重试
	private AtomicInteger reConn = new AtomicInteger(3);
	// 连接断开,并且尝试恢复失败
	private AtomicBoolean isBroken = new AtomicBoolean(false);
	private static class LazyHolder {    
		private static final ConnectionManager INSTANCE = new ConnectionManager();    
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static ConnectionManager getInstance() {
	    return LazyHolder.INSTANCE; 
	}
	
	final class DefaultShutdownListener implements ShutdownListener{
		@Override
		public void shutdownCompleted(ShutdownSignalException cause) {
			LOGGER.warn("已成功关闭一个connection,{}",cause.getLocalizedMessage());
			try {
				LOGGER.warn("尝试重新建立连接");
				while (!connection.isOpen()) {
					if(reConn.decrementAndGet() == 0){
						isBroken.set(true);
						throw new RabbitmqConnException("连接已断开:"+config.toString());
					}
					if(!channels.isEmpty()){
						channels.clear();
					}
					Thread.sleep(2000);
					connection = factory.newConnection();
					connection.addShutdownListener(new DefaultShutdownListener());
					if(connection != null && connection.isOpen()){
						if(isBroken.get()){
							isBroken.set(false);
						}
						reConn.set(3);
						LOGGER.warn("连接已经重新建立:"+config.toString());
					}
				}
			} catch (IOException e) {
				LOGGER.warn("尝试重新建立连接失败:"+e.getMessage(),e);
				reConn.set(0);
				isBroken.set(true);
				throw new RabbitmqConnException("连接已断开:"+config.toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void init(String resource) {
		if(isInit){
			return;
		}
		if(resource == null){
			resource = DEFAULT_RESOURCE;
		}
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			LOGGER.error("获取 RabbitMQ 配置失败,{}",e);
		}
		this.config = RmqConfig.buildConfig(p);
		factory.setHost(config.getServerIp());
		factory.setPort(config.getServerPort());
		factory.setVirtualHost(config.getVirtualHost());
		factory.setUsername(config.getUsername());
		factory.setPassword(config.getPassword());
		factory.setConnectionTimeout(config.getConnection_timeout());
		factory.setShutdownTimeout(config.getShutdown_timeout());
		//factory.setSharedExecutor(Executors.newFixedThreadPool(50));// 这里只用开一个连接,暂不用线程池
		try {
			connection = factory.newConnection();
			connection.addShutdownListener(new DefaultShutdownListener());
		} catch (IOException e) {
			isInit = false;
			LOGGER.error("建立连接失败,{}",e);
		}
		isInit = true;
	}
	
	/**
	  * @return Channel 
	  * @author cjh
	 * @throws IOException 
	 */
	public Channel createChannel() {
		Thread thread = Thread.currentThread();
		Channel channel = null;
		try {
			channel = channels.get(thread);
			if(channel == null){
				channel = connection.createChannel();
				channels.put(thread, channel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	public void removeChannel(Thread thread) {
		if (channels.containsKey(thread)) {
			channels.remove(thread);
		}
	}
	
	/**
	 * 针对消费者 新建Channel 跟发送消息分开
	 */
	public Channel newChannel() {
		Channel channel = null;
		try {
			channel = connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	public void shutdown() {
		try {
			if(connection.isOpen()){
				connection.close();
			}
		} catch (IOException e) {
			LOGGER.error("关闭RabbitMQ连接失败,{}",e);
		}
	}
	
	public RmqConfig getConfig() {
		return config;
	}

	public void setConfig(RmqConfig config) {
		this.config = config;
	}
	
	public boolean isBroken() {
		return isBroken.get();
	}
	
	public boolean isInit() {
		return isInit;
	}
}
