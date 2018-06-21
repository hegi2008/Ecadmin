package com.yinhai.plugin.fs.mongo.template;

import java.util.ArrayList;
import java.util.List;

import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

/**
* @package com.yinhai.plugin.fs.mongo.template
* <p>Title: MongoTemplate.java</p>
* <p>Description: MongoDB 操作类,请在项目中保证为单例  可以获取各个读写模式的database实例</p>
* @author cjh
* @date 2016年6月27日 上午11:10:24
* @version 1.0
 */
public class MongoTemplate{
	private MongoClient client = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoTemplate.class);
	
	public MongoTemplate() {
		this.connectionsPerHost = 10;
		this.threadsAllowedToBlockForConnectionMultiplier = 5;
		this.connectTimeout = 2000;
		this.maxWaitTime = 1500;
		this.isNeedAuthentication = false;
		this.readConcern = ReadConcern.DEFAULT;
		this.readPreference = ReadPreference.primary();
    }
	
	public MongoDatabase getMongoDatabase(String dbName) {
		if (client != null) {
			return client.getDatabase(dbName);
        }
		return null;
	}
	
	public MongoDatabase getMongoDatabase() {
		return getMongoDatabase(this.dbname);
	}
	
	public MongoDatabase getMongoDatabase(ReadPreference readPreference) {
		Assertions.notNull("readPreference",readPreference);
		return getMongoDatabase().withReadPreference(readPreference);
	}
	
	public MongoDatabase getMongoDatabase(String dbName, ReadPreference readPreference) {
		Assertions.notNull("readPreference",readPreference);
		return getMongoDatabase(dbName).withReadPreference(readPreference);
	}
	
	public MongoDatabase getMongoDatabase(WriteConcern writeConcern) {
		Assertions.notNull("writeConcern",writeConcern);
		return getMongoDatabase().withWriteConcern(writeConcern);
	}
	
	public MongoDatabase getMongoDatabase(CodecRegistry codecRegistry) {
		Assertions.notNull("codecRegistry",codecRegistry);
		return getMongoDatabase().withCodecRegistry(codecRegistry);
	}
	
	public MongoDatabase getMongoDatabase(String dbName, CodecRegistry codecRegistry) {
		Assertions.notNull("codecRegistry",codecRegistry);
		return getMongoDatabase(dbName).withCodecRegistry(codecRegistry);
	}
	
	public MongoDatabase getMongoDatabase(String dbName, WriteConcern writeConcern) {
		Assertions.notNull("writeConcern",writeConcern);
		return getMongoDatabase(dbName).withWriteConcern(writeConcern);
	}
	
	public MongoClient getClient() {
		return this.client;
	}

	/**
	  * @package com.yinhai.ec.fs.mongodb.manager
	  * @method init 方法 
	  * @describe <p>方法说明:初始化连接池，设置参数。</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年5月24日 上午10:43:08
	 */
    public void initMongo() {
    	LOGGER.debug("init mongoClient ...");
    	List<ServerAddress> seeds = new ArrayList<ServerAddress>();
    	List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
    	
        // 连接池参数
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.connectionsPerHost(this.connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier);
        builder.maxWaitTime(this.maxWaitTime);
        builder.connectTimeout(this.connectTimeout);
        builder.socketTimeout(this.socketTimeout);
        builder.socketKeepAlive(this.socketKeepAlive);
        builder.readConcern(this.readConcern);
        builder.readPreference(this.readPreference);
        MongoClientOptions options = builder.build();
        MongoCredential credential = null;
        
        if(isNeedAuthentication){
        	Assertions.notNull("username",username);
        	Assertions.notNull("dbname",dbname);
        	Assertions.notNull("password",password);
        	credential = MongoCredential.createCredential(username, dbname, password.toCharArray());
        	credentialsList.add(credential);
        }
        
        try {
        	String[] strings = servers.split(",");
        	for (String string : strings) {
        		ServerAddress address = new ServerAddress(string.split(":")[0],Integer.valueOf(string.split(":")[1]));
        		seeds.add(address);
    		}
        	if(credentialsList.size() > 0){
        		client = new MongoClient(seeds, credentialsList, options);
        	}else{
        		client = new MongoClient(seeds, options);
        	}
        	LOGGER.debug("init mongoClient finished, no error happened");
        } catch (MongoException e) {
        	if (LOGGER.isErrorEnabled()) {
				LOGGER.error("init mongoClient has an error,{}",e);
			}
        }
    }
    private int connectionsPerHost;
    private int threadsAllowedToBlockForConnectionMultiplier;
    private int connectTimeout;
    private int maxWaitTime;
    private int socketTimeout;
    private boolean socketKeepAlive;
    private boolean autoConnectRetry;
    private boolean isNeedAuthentication;
    private ReadConcern readConcern;
    private ReadPreference readPreference;
    private String username;
    private String password;
    private String dbname;
    private String servers;

	public int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public boolean isNeedAuthentication() {
		return isNeedAuthentication;
	}

	public void setNeedAuthentication(boolean isNeedAuthentication) {
		this.isNeedAuthentication = isNeedAuthentication;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public boolean isAutoConnectRetry() {
		return autoConnectRetry;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

	public boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}

	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}

	public ReadConcern getReadConcern() {
		return readConcern;
	}

	public void setReadConcern(ReadConcern readConcern) {
		this.readConcern = readConcern;
	}

	public ReadPreference getReadPreference() {
		return readPreference;
	}

	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}
}