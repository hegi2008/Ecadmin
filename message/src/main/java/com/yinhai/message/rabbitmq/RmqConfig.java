package com.yinhai.message.rabbitmq;

import java.util.Properties;

import com.rabbitmq.client.AMQP;

/**
* @package com.yinhai.message.rabbitmq
* <p>Title: RmqConfig.java</p>
* <p>Rabbitmq 配置</p>
* @author cjh
* @date 2016年11月15日 上午10:19:13
 */
public class RmqConfig {
	/** Default user name */
    public static final String DEFAULT_USER = "guest";
    /** Default password */
    public static final String DEFAULT_PASS = "guest";
    /** The default non-ssl port */
    public static final int    DEFAULT_AMQP_PORT = AMQP.PROTOCOL.PORT;
    /** Default virtual host */
    public static final String DEFAULT_VHOST = "/";
    /** The default connection timeout;
     *  zero means wait indefinitely */
    public static final int    DEFAULT_CONNECTION_TIMEOUT = 0;
    /** The default shutdown timeout;
     *  zero means wait indefinitely */
    public static final int    DEFAULT_SHUTDOWN_TIMEOUT = 10000;
    
    public static final String DEFAULT_EXCHANGE = null;
    
    public static final String DEFAULT_EXCHANGETYPE = null;
    
    public static final String DEFAULT_SERVERIP = "localhost";
    
    public static final String DEFAULT_QUEUE = "rmq.default.queue";
    
	private String serverIp = DEFAULT_SERVERIP;
	private int serverPort = DEFAULT_AMQP_PORT;
	private int connection_timeout = DEFAULT_CONNECTION_TIMEOUT;
	private int shutdown_timeout = DEFAULT_SHUTDOWN_TIMEOUT;
	private String exchange = DEFAULT_EXCHANGE;
	private String exchangeType = DEFAULT_EXCHANGETYPE;
	private String username = DEFAULT_USER;
	private String password = DEFAULT_PASS;
	private String queue = DEFAULT_QUEUE;
	private String virtualHost = DEFAULT_VHOST;
	
	/**queue 是否需要持久化(重启server不丢失) 默认false */
	private boolean durable_queue = false;
	/**exchange 是否需要持久化(重启server不丢失) 默认false */
	private boolean durable_exchange = false;
	/**queue 是否在不使用的时候自动删除 默认false*/
	private boolean autoDelete_queue = false;
	/**exchange 是否在不使用的时候自动删除 默认false*/
	private boolean autoDelete_exchange = false;
	/**queue 是否和当前连接绑定,专有queue 默认false*/
	private boolean exclusive_queue = false;
	
	public RmqConfig() {
		
	}
	
	public RmqConfig(String serverIp, int serverPort, String exchange, String exchangeType, String username, String password
			,int connection_timeout, int shutdown_timeout, String queue, String virtualHost) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.exchange = exchange;
		this.exchangeType = exchangeType;
		this.username = username;
		this.password = password;
		this.connection_timeout = connection_timeout;
		this.shutdown_timeout = shutdown_timeout;
		this.queue = queue;
		this.virtualHost = virtualHost;
	}
	
	public static RmqConfig buildConfig(Properties p) {
		String serverIp = p.getProperty("rabbitmq.serverIp",DEFAULT_SERVERIP);
		int serverPort = Integer.valueOf(p.getProperty("rabbitmq.serverPort", DEFAULT_AMQP_PORT+""));
		String exchange = p.getProperty("rabbitmq.exchange",DEFAULT_EXCHANGE);
		String exchangeType = p.getProperty("rabbitmq.exchangeType",DEFAULT_EXCHANGETYPE);
		String username = p.getProperty("rabbitmq.username",DEFAULT_USER);
		String password = p.getProperty("rabbitmq.password",DEFAULT_PASS);
		int connection_timeout = Integer.valueOf(p.getProperty("rabbitmq.connection_timeout", DEFAULT_CONNECTION_TIMEOUT+""));
		int shutdown_timeout = Integer.valueOf(p.getProperty("rabbitmq.shutdown_timeout", DEFAULT_SHUTDOWN_TIMEOUT+""));
		String queue = p.getProperty("rabbitmq.queue",DEFAULT_QUEUE);
		String virtualHost = p.getProperty("rabbitmq.virtualHost",DEFAULT_VHOST);
		RmqConfig config = new RmqConfig(serverIp, serverPort, exchange, exchangeType, username, password, connection_timeout, shutdown_timeout, queue, virtualHost);
		config.setDurable_queue(Boolean.valueOf(p.getProperty("rabbitmq.durable_queue","false")));
		config.setDurable_exchange(Boolean.valueOf(p.getProperty("rabbitmq.durable_exchange","false")));
		config.setAutoDelete_queue(Boolean.valueOf(p.getProperty("rabbitmq.autoDelete_queue","false")));
		config.setAutoDelete_exchange(Boolean.valueOf(p.getProperty("rabbitmq.autoDelete_exchange","false")));
		config.setExclusive_queue(Boolean.valueOf(p.getProperty("rabbitmq.exclusive_queue","false")));
		return config;
	}
	
	enum ExchangeType {
		
		DIRECT("direct"),
		FANOUT("fanout"),
		TOPIC("topic"),
		HEADERS("headers");
		private String value;
		
		ExchangeType(String value) {
			this.value = value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RmqConfig other = (RmqConfig) obj;
        if (serverIp == null) {
            if (other.serverIp != null) {
            	return false;
            }
        } else if (!serverIp.equals(other.serverIp)){
        	return false;
        }
        if(serverPort != other.serverPort){
        	return false;
        }
        if(connection_timeout != other.connection_timeout){
        	return false;
        }
        if(shutdown_timeout != other.shutdown_timeout){
        	return false;
        }
        if (exchange == null) {
            if (other.exchange != null) {
            	return false;
            }
        } else if (!exchange.equals(other.exchange)){
        	return false;
        }
        if (exchangeType == null) {
            if (other.exchangeType != null) {
            	return false;
            }
        } else if (!exchangeType.equals(other.exchangeType)){
        	return false;
        }
        if (username == null) {
            if (other.username != null) {
            	return false;
            }
        } else if (!username.equals(other.username)){
        	return false;
        }
        if (password == null) {
            if (other.password != null) {
            	return false;
            }
        } else if (!password.equals(other.password)){
        	return false;
        }
        if (queue == null) {
            if (other.queue != null) {
            	return false;
            }
        } else if (!queue.equals(other.queue)){
        	return false;
        }
        if (virtualHost == null) {
            if (other.virtualHost != null) {
            	return false;
            }
        } else if (!virtualHost.equals(other.virtualHost)){
        	return false;
        }
        return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((serverIp == null) ? 0 : serverIp.hashCode());
        result = prime * result + serverPort;
        result = prime * result + connection_timeout;
        result = prime * result + shutdown_timeout;
        result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
        result = prime * result + ((exchangeType == null) ? 0 : exchangeType.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((queue == null) ? 0 : queue.hashCode());
        result = prime * result + ((virtualHost == null) ? 0 : virtualHost.hashCode());
        return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("amqp://");
		builder.append(username).append(":").append(password).append("@");
		builder.append(serverIp).append(":").append(serverPort).append(virtualHost);
		return builder.toString();
	}
	
	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getExchange() {
		return exchange;
	}

	public String getExchangeType() {
		return exchangeType;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getConnection_timeout() {
		return connection_timeout;
	}

	public int getShutdown_timeout() {
		return shutdown_timeout;
	}

	public String getQueue() {
		return queue;
	}

	public boolean isDurable_queue() {
		return durable_queue;
	}

	public void setDurable_queue(boolean durable_queue) {
		this.durable_queue = durable_queue;
	}

	public boolean isDurable_exchange() {
		return durable_exchange;
	}

	public void setDurable_exchange(boolean durable_exchange) {
		this.durable_exchange = durable_exchange;
	}

	public boolean isAutoDelete_queue() {
		return autoDelete_queue;
	}

	public void setAutoDelete_queue(boolean autoDelete_queue) {
		this.autoDelete_queue = autoDelete_queue;
	}

	public boolean isAutoDelete_exchange() {
		return autoDelete_exchange;
	}

	public void setAutoDelete_exchange(boolean autoDelete_exchange) {
		this.autoDelete_exchange = autoDelete_exchange;
	}

	public boolean isExclusive_queue() {
		return exclusive_queue;
	}

	public void setExclusive_queue(boolean exclusive_queue) {
		this.exclusive_queue = exclusive_queue;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setConnection_timeout(int connection_timeout) {
		this.connection_timeout = connection_timeout;
	}

	public void setShutdown_timeout(int shutdown_timeout) {
		this.shutdown_timeout = shutdown_timeout;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}
}
