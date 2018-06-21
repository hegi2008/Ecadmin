package com.yinhai.ec.base.redis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;


@SuppressWarnings("deprecation")
public class RedisClient {
	private Logger log = Logger.getLogger(RedisClient.class);
	
	private JedisPool writePool;//写连接池
	private JedisPool readPool;//读连接池
	
	private JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
	
	private String writeHost;//Keepalived的VIP，虚拟IP地址
	private String readHost;//Haproxy的负载均衡地址
	
	private int writePort;//Keepalived的代理redis端口
	private int readPort;//Haproxy集群的端口
	
	private int maxActive;
	private int maxIdle;
	private int maxWait;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private int timeOut;
	private String password;
	

	/**
	 * 初始化
	 */
	public void initialize() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		writePool = new JedisPool(config, writeHost, writePort, timeOut,password);
		readPool = new JedisPool(config, readHost, readPort, timeOut,password);
	}
	
	
	/**
	 * 获取写连接池资源，writePool
	 * @return
	 */
	private Jedis getWriteJedisResource(){
		try {
			Jedis jedis = writePool.getResource();
			return jedis;
		} catch (Exception e) {
			log.error("redis获取写连接池失败");
		}
		return null;
	}
	/**
	 * 获取读连接池资源，readPool
	 * @return
	 */
	private Jedis getReadJedisResource(){
		try {
			Jedis jedis = readPool.getResource();
			return jedis;
		} catch (Exception e) {
			log.error("redis获取读连接池失败");
		}
		return null;
	}
	

	/**
	 * 设置缓存String
	 * @param key
	 * @param value 
	 */
	public void setString(String key, String value) {
		Jedis jedis = getWriteJedisResource();
		try {
			jedis.set(key,value);
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 设置缓存String
	 * @param key
	 * @param value
	 * @param exp 过期时间(秒)
	 */
	public void setString(String key, String value,long exp) {
		Jedis jedis = getWriteJedisResource();
		try {
			if(jedis.exists(key)){
				jedis.set(key,value,"XX","EX",exp);
			}else{
				jedis.set(key,value,"NX","EX",exp);
			}			
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 设置缓存Object
	 * @param key
	 * @param value
	 */
	public void setObject(String key, Object value) {
		Jedis jedis = getWriteJedisResource();
		try {
			jedis.set(key.getBytes(), toBytes(value));
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 设置缓存Object
	 * @param key
	 * @param value
	 * @param exp 过期时间(秒)
	 */
	public void setObject(String key, Object value,long exp) {
		Jedis jedis = getWriteJedisResource();
		try {
			if(jedis.exists(key.getBytes())){
				jedis.set(key.getBytes(),toBytes(value),"XX".getBytes(),"EX".getBytes(),exp);
			}else{
				jedis.set(key.getBytes(),toBytes(value),"NX".getBytes(),"EX".getBytes(),exp);
			}	
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public String getString(String key){
		Jedis jedis = getReadJedisResource();
		try {
			return jedis.get(key);
		}finally {
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public Object getObj(String key){
		Jedis jedis = getReadJedisResource();
		try {
			return toObject(jedis.get(key.getBytes()));
		}finally {
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 根据表达式取得所匹配的所有键
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern){
		Jedis jedis = getWriteJedisResource();
		try {
			return jedis.keys(pattern);
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 删除key对应的值
	 * @param key
	 */
	public void del(String key){
		Jedis jedis = getWriteJedisResource();
		try {
			jedis.del(key);
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 删除key对应的值
	 * @param key
	 */
	public void delObj(String key){
		Jedis jedis = getWriteJedisResource();
		try {
			jedis.del(key.getBytes());
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}

	/**
	 * 设置过期时间
	 * @param key 
	 * @param seconds 秒
	 */
	public void expire(String key,int seconds){
		Jedis jedis = getWriteJedisResource();
		try {
			jedis.expire(key.getBytes(), seconds);
		}finally {
			writePool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 查看key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		Jedis jedis = getReadJedisResource();
		try {
			return jedis.exists(key.getBytes());
		}finally {
			readPool.returnResourceObject(jedis);
		}
	}
	/**
	 * 查看key是否存在
	 * @param key
	 * @return
	 */
	public boolean existsStr(String key){
		Jedis jedis = getReadJedisResource();
		try {
			return jedis.exists(key);
		}finally {
			readPool.returnResourceObject(jedis);
		}
	}	
	/**
	 * 将一个或多个值value插入到列表key的表头。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lpush(key, value);
		}finally{
			readPool.returnResourceObject(jedis);
		}		
	}
	
	/**
	 * 将一个或多个值value插入到列表key的表头。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long pushObj(String key, Object value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lpush(key.getBytes(), toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}		
	}

	/**
	 * 移除并返回列表key的头元素。
	 * @param key
	 * @return
	 */
	public Object pop(String key) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lpop(key);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 *  将一个或多个值value插入到列表key的表尾。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.rpush(key, value);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 *  将一个或多个值value插入到列表key的表尾。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long inObj(String key, Object value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.rpush(key.getBytes(), toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 移除并返回列表key的尾元素。
	 * 
	 * @param key
	 * @return
	 */
	public String out(String key) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.rpop(key);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 移除并返回列表key的尾元素。
	 * 
	 * @param key
	 * @return
	 */
	public Object outObj(String key) {
		Jedis jedis = getReadJedisResource();
		try{
			return toObject(jedis.rpop(key.getBytes()));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.llen(key);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long lengthObj(String key) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.llen(key.getBytes());
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 返回列表
	 * 返回列表key中，下标为index的元素。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推 如果key不是列表类型，返回一个错误。<br>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> range(String key, int start, int end) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lrange(key, start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回列表
	 * 返回列表key中，下标为index的元素。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推 如果key不是列表类型，返回一个错误。<br>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> rangeObj(String key, int start, int end) {
		Jedis jedis = getReadJedisResource();
		try{
			List<byte[]> ranglist = jedis.lrange(key.getBytes(), start, end);
			List<Object> back = new ArrayList<Object>();
			for (byte[] bs : ranglist) {
				back.add(toObject(bs));
			}
			return back;
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 移除列表中与参数
	 * 根据参数count的值，移除列表中与参数value相等的元素。
	 * @param key
	 * @param i
	 * @param value
	 */
	public Long remove(String key, long i, String value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lrem(key, i, value);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 移除列表中与参数
	 * 根据参数count的值，移除列表中与参数value相等的元素。
	 * @param key
	 * @param i
	 * @param value
	 */
	public Long removeObj(String key, long i, Object value) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lrem(key.getBytes(), i, toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 检索<br>
	 *  返回列表key中，下标为index的元素。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推 如果key不是列表类型，返回一个错误。<br>
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String index(String key, long index) {
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.lindex(key, index);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 检索<br>
	 *  返回列表key中，下标为index的元素。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推 如果key不是列表类型，返回一个错误。<br>
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public Object indexObj(String key, long index) {
		Jedis jedis = getReadJedisResource();
		try{
			return toObject(jedis.lindex(key.getBytes(), index));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 重置指定元素值
	 * 将列表key下标为index的元素的值甚至为value。<br>
	 * @param key
	 * @param index
	 * @param value
	 */
	public void setIndexValue(String key, long index, String value) {
		Jedis jedis = getReadJedisResource();
		try{
			jedis.lset(key, index, value);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 重置指定元素值
	 * 将列表key下标为index的元素的值甚至为value。<br>
	 * @param key
	 * @param index
	 * @param value
	 */
	public void setIndexValueObj(String key, long index, Object value) {
		Jedis jedis = getReadJedisResource();
		try{
			jedis.lset(key.getBytes(), index, toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 修剪 <br>
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。<br>
	 * 举个例子，执行命令LTRIM list 0 2，表示只保留列表list的前三个元素，其余元素全部删除。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。<br>
	 * 当key不是列表类型时，返回一个错误。<br>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, int end) {
		Jedis jedis = getReadJedisResource();
		try{
			jedis.ltrim(key, start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 修剪 <br>
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。<br>
	 * 举个例子，执行命令LTRIM list 0 2，表示只保留列表list的前三个元素，其余元素全部删除。<br>
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。<br>
	 * 你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。<br>
	 * 当key不是列表类型时，返回一个错误。<br>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trimObj(String key, long start, int end) {
		Jedis jedis = getReadJedisResource();
		try{
			jedis.ltrim(key.getBytes(), start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 添加指定的成员到key对应的有序集合中，每个成员都有一个分数。你可以指定多个分数/成员组合。
	 * 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序。
	 * 如果key不存在，就会创建一个含有这些成员的有序集合，就好像往一个空的集合中添加一样。
	 * 如果key存在，但是它并不是一个有序集合，那么就返回一个错误。
	 * @param key 
	 * @param value
	 * @param score 
	 * @return
	 */
	public Long zadd(String key,String value,double score){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zadd(key, score, value);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 添加指定的成员到key对应的有序集合中，每个成员都有一个分数。你可以指定多个分数/成员组合。
	 * 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序。
	 * 如果key不存在，就会创建一个含有这些成员的有序集合，就好像往一个空的集合中添加一样。
	 * 如果key存在，但是它并不是一个有序集合，那么就返回一个错误。
	 * @param key 
	 * @param value
	 * @param score 
	 * @return
	 */
	public Long zaddObj(String key,Object value,double score){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zadd(key.getBytes(), score, toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集key中，score值在min和max之间(默认包括score值等于min或max)的成员。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zcount(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zcount(key, min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集key中，score值在min和max之间(默认包括score值等于min或max)的成员。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zcountObj(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zcount(key.getBytes(), min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集key中，指定区间内的成员。其中成员按score值递增(从小到大)来排序。
	 * 具有相同score值的成员按字典序来排列。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key,long start,long end){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrange(key, start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集key中，指定区间内的成员。其中成员按score值递增(从小到大)来排序。
	 * 具有相同score值的成员按字典序来排列。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<byte[]> zrangeObj(String key,long start,long end){
		Jedis jedis = getReadJedisResource();
		try{
			Set<byte[]> rangeset = jedis.zrange(key.getBytes(), start, end);
			Set<byte[]> back = new LinkedHashSet<byte[]>();
			for (byte[] bs : rangeset) {
				back.add((byte[]) toObject(bs));
			}
			return back;
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 * 有序集成员按 score 值递增(从小到大)次序排列。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrangeByScore(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrangeByScore(key, min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 * 有序集成员按 score 值递增(从小到大)次序排列。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<byte[]> zrangeByScoreObj(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			Set<byte[]> ranglist = (Set<byte[]>) jedis.zrangeByScore(key.getBytes(), min, max);
			Set<byte[]> back = new LinkedHashSet<byte[]>();
			for (byte[] bs : ranglist) {
				back.add((byte[]) toObject(bs));
			}
			return back;			
			//return jedis.zrangeByScore(key.getBytes(), min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	public Set<Tuple> zrangeByScoreWithScores(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrangeByScoreWithScores(key, min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	public Set<Tuple> zrangeByScoreWithScoresObj(String key,double min,double max){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrangeByScoreWithScores(key.getBytes(), min, max);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	public Set<Tuple> zrangeWithScores(String key,long start,long end){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrangeWithScores(key, start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	public Set<Tuple> zrangeWithScoresObj(String key,long start,long end){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.zrangeWithScores(key.getBytes(), start, end);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 批量删除前缀为key的所有值
	 * @param key
	 */
	public void emoveByPrefix(String key){
		Jedis jedis = getReadJedisResource();
		try{
			Set<?> keys = jedis.keys(key);
			jedis.del(keys.toArray(new String[keys.size()]));
		}catch(Exception e){
			log.error("批量删除：\""+key+" \" key不存在",e);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加1
	 * @param key
	 * @return
	 */
	public Long incr(String key){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.incr(key);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	} 
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加1
	 * @param key
	 * @return
	 */
	public Long incrObj(String key){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.incr(key.getBytes());
		}finally{
			readPool.returnResourceObject(jedis);
		}
	} 
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加i
	 * @param key
	 * @return
	 */
	public Long incrBy(String key,long i){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.incrBy(key,i);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加i
	 * @param key
	 * @return
	 */
	public Long incrByObj(String key,long i){
		Jedis jedis = getReadJedisResource();
		try{
			return jedis.incrBy(key.getBytes(),i);
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。
	 * @param key
	 * @param value
	 * @return key不存在设置成功返回true，反之key存在，返回false
	 */
	public boolean setNX(String key,String value){
		Jedis jedis = getReadJedisResource();
		try{
			Long b = jedis.setnx(key, value);
			if(b==0){//设置不成功
				return false;
			}else{
				return true;
			}
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。
	 * @param key
	 * @param value
	 * @return key不存在设置成功返回true，反之key存在，返回false
	 */
	public boolean setNXObj(String key,Object value){
		Jedis jedis = getReadJedisResource();
		try{
			Long b = jedis.setnx(key.getBytes(), toBytes(value));
			if(b==0){//设置不成功
				return false;
			}else{
				return true;
			}
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 更新List中最后一个value
	 * @param key
	 * @param value
	 */
	public void setNew(String key,Object value){
		Jedis jedis = getReadJedisResource();
		try{
			jedis.lset(key.getBytes(), -1, toBytes(value));
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 获取List中的最后一项
	 * @param key
	 * @return
	 */
	public Object getLast(String key){
		Jedis jedis = getReadJedisResource();
		try{
			List<byte[]> list = jedis.lrange(key.getBytes(),-1,-1);
			if(list!=null&&list.size()>0){
				return toObject(list.get(0));
			}else{
				return null;
			}
		}finally{
			readPool.returnResourceObject(jedis);
		}
	}

	/**
	 * 反序列化对象
	 * @param bs
	 * @return
	 */
	private Object toObject(byte[] bs){
		return jdkSerializationRedisSerializer.deserialize(bs);
	}

	/**
	 * 序列化对象
	 * @param ret
	 * @return
	 */
	private byte[] toBytes(Object ret) {
		return jdkSerializationRedisSerializer.serialize(ret);
	}
	
	public JedisPool getWritePool() {
		return writePool;
	}


	public void setWritePool(JedisPool writePool) {
		this.writePool = writePool;
	}


	public JedisPool getReadPool() {
		return readPool;
	}


	public void setReadPool(JedisPool readPool) {
		this.readPool = readPool;
	}


	public String getWriteHost() {
		return writeHost;
	}


	public void setWriteHost(String writeHost) {
		this.writeHost = writeHost;
	}


	public String getReadHost() {
		return readHost;
	}


	public void setReadHost(String readHost) {
		this.readHost = readHost;
	}


	public int getWritePort() {
		return writePort;
	}


	public void setWritePort(int writePort) {
		this.writePort = writePort;
	}


	public int getReadPort() {
		return readPort;
	}


	public void setReadPort(int readPort) {
		this.readPort = readPort;
	}


	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
