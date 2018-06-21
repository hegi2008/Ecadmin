package com.yinhai.message.rabbitmq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @package com.yinhai.message.rabbitmq
 *          <p>
 *          Title: RabbitMQMessage.java
 *          </p>
 *          <p>
 *          消息类,保存调用哪个路由key和交换器（也是走哪条线）、要传的数据
 *          </p>
 * @author cjh
 * @date 2016年11月15日 上午9:12:01
 */
public class RabbitMQMessage implements Serializable {

	private static final long serialVersionUID = -3258076291241698484L;
	private Class<?>[] paramTypes;// 参数类型
	private String exchange;// 交换器
	private Object[] params;
	private String routeKey;// 路由key

	public RabbitMQMessage() {
	}

	public RabbitMQMessage(String exchange, String routeKey, Object... params) {
		this.params = params;
		this.exchange = exchange;
		this.routeKey = routeKey;
		int len = params.length;
		Class<?>[] clazzArray = new Class<?>[len];
		for (int i = 0; i < len; i++) {
			clazzArray[i] = params[i].getClass();
		}
		this.paramTypes = clazzArray;
	}

	public byte[] getSerialBytes() {
		byte[] res = new byte[0];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			res = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class<?>[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getRouteKey() {
		return routeKey;
	}

	public void setRouteKey(String routeKey) {
		this.routeKey = routeKey;
	}
}
