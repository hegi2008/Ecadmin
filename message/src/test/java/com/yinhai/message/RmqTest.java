package com.yinhai.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.rabbitmq.client.GetResponse;
import com.yinhai.message.rabbitmq.connection.ConnectionManager;
import com.yinhai.message.rabbitmq.consumer.BlockingQueueConsumer;
import com.yinhai.message.rabbitmq.consumer.LongConnectionConsumer;
import com.yinhai.message.rabbitmq.consumer.OncePerConsumer;
import com.yinhai.message.rabbitmq.producer.RmqProducer;

public class RmqTest {
	private String resource = "rabbitmq-test.properties";
	
	@Test
	public void testProducer1() {
		// 100个线程 100条消息
		ConnectionManager.getInstance().init(resource);
		final RmqProducer producer = new RmqProducer();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					producer.sendMessage("说好的三年不见面");
				}
			});
			thread.start();
		}
		while (true) {
			
		}
	}
	
	@Test
	public void testProducer2() {
		ConnectionManager.getInstance().init(resource);
		// 一个线程 100条消息
		final RmqProducer producer = new RmqProducer();
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", "向天");
		map.put("age", "27");
		for (int i = 0; i < 100; i++) {
			producer.sendMessage(map);
		}
		while (true) {
			
		}
	}
	
	@Test
	public void testExecutor() {
		ConnectionManager.getInstance().init(resource);
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
		final RmqProducer producer = new RmqProducer();
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				producer.sendMessage("说好的三年不见面:"+System.currentTimeMillis()/1000);
			}
		}, 1, 2, TimeUnit.SECONDS);
		
		while (true) {
			//System.out.println(scheduledExecutorService.toString());
		}
	}
	
	@Test
	public void testConsumer() throws IOException{
		ConnectionManager.getInstance().init(resource);
		LongConnectionConsumer consumer = new LongConnectionConsumer();
		Thread thread = new Thread(consumer);
		thread.start();
		while(true){
			if (consumer.isStopConsume()) {
				if (ConnectionManager.getInstance().getConnection() != null
						&& ConnectionManager.getInstance().getConnection().isOpen()) {
					consumer = new LongConnectionConsumer();
					consumer.setChannel(ConnectionManager.getInstance().createChannel());
					consumer.channel.basicConsume(ConnectionManager.getInstance().getConfig().getQueue(), consumer.isAutoAck(), consumer);
				}
			}
		}
	}
	
	@Test
	public void testConsumer2() throws InterruptedException{
		ConnectionManager.getInstance().init(resource);
		BlockingQueueConsumer consumer = new BlockingQueueConsumer();
		consumer.consume();
		while(true){
			System.out.println(consumer.next().toString());
			Thread.sleep(5000);
			if (consumer.isStopConsume()) {
				if (ConnectionManager.getInstance().getConnection() != null
						&& ConnectionManager.getInstance().getConnection().isOpen()) {
					consumer = new BlockingQueueConsumer();
					consumer.setChannel(ConnectionManager.getInstance().newChannel());
					consumer.consume();
				}
			}
		}
	}
	
	@Test
	public void testConsumer3() throws InterruptedException {
		ConnectionManager.getInstance().init(resource);
		OncePerConsumer consumer = new OncePerConsumer();
		while (true) {
			Thread.sleep(2000);
			GetResponse GetResponse = consumer.getMessage();
			if(GetResponse != null){
				if (!consumer.isAutoAck()) {
					consumer.ackMessage(GetResponse.getEnvelope().getDeliveryTag());
				}
				Object object = consumer.toObject(GetResponse.getBody());
				System.out.println(object);
			}
		}
	}
}
