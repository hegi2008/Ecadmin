package com.yinhai.ec.base.redis;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * 功能描述：使用Redis作为外部缓存，于统一实现缓存存取接口，未来如果API发生变化，
 * 只修改本类即可，不干扰业务代码。本类只提供了部分Redis中的接口，如需要增加，请修改本类，添加对应的接口。
 * 
 * @author maomao
 * @version 2.0
 */

@SuppressWarnings("rawtypes")
@Service
public class RedisAdapter {
	Logger log = Logger.getLogger(getClass());
	private RedisClient redisClient;
	
	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	/**
	 * 判断是否连接服务器
	 * @return
	 */
//	public boolean isExposeConnection(){
//		return this.cache.isExposeConnection();
//	}
	
	/**
	 * 设置缓存值
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void set(String key, String value) {
			redisClient.setString(key, value);
	}

	/**
	 * 设置缓存值
	 * 
	 * @param <T>
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param exp
	 *            值长度，不够此长度，以空格填充
	 */
	public void set(String key, String value, long exp) {
		redisClient.setString(key, value, exp);
	}

	/**
	 * 根据键值获取缓存数据
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisClient.getString(key);
	}

	/**
	 * 根据键值删除缓存数据
	 * @param <T>
	 * @param key
	 */
	public <T> void del(String key) {
		redisClient.del(key);
	}

	/**
	 * 将一个或多个值value插入到列表key的表头。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String value) {
		return redisClient.push(key, value);
	}

	/**
	 * 移除并返回列表key的头元素。
	 * @param key
	 * @return
	 */
	public Object pop(String key) {
		return redisClient.pop(key);
	}
	
	/**
	 *  将一个或多个值value插入到列表key的表尾。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String value) {
		return redisClient.in(key, value);
	}

	/**
	 * 移除并返回列表key的尾元素。
	 * 
	 * @param key
	 * @return
	 */
	public String out(String key) {
		return redisClient.out(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		return redisClient.length(key);
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
		return redisClient.range(key, start, end);
	}

	/**
	 * 移除列表中与参数
	 * 根据参数count的值，移除列表中与参数value相等的元素。
	 * @param key
	 * @param i
	 * @param value
	 */
	public void remove(String key, long i, String value) {
		redisClient.remove(key, i, value);
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
		return redisClient.index(key, index);
	}

	/**
	 * 重置指定元素值
	 * 将列表key下标为index的元素的值甚至为value。<br>
	 * @param key
	 * @param index
	 * @param value
	 */
	public void setIndexValue(String key, long index, String value) {
		redisClient.setIndexValue(key, index, value);
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
		redisClient.trim(key, start, end);
	}
	
	
	public Long zadd(String key,String value,double score){
		return redisClient.zadd(key, value, score);
	}
	
	public long zcount(String key,double min,double max){
		return redisClient.zcount(key, min, max);
	}
	
	public Set zrange(String key,long start,long end){
		return redisClient.zrange(key, start, end);
	}
	
	public void emoveByPrefix(String key){
		try{
			Set<String> keys = redisClient.keys(key);
			if(keys!=null&&keys.size()>0){
				for (String k : keys) {
					redisClient.del(k);
				}
			}
		}catch(Exception e){
			log.error("批量删除：\""+key+" \" key不存在",e);
		}
	}
	

	/**
	 * 设置KEY的过期时间
	 * @param key
	 * @param time 过期时间
	 * @param timeUnit TimeUnit常量
	 */
	public void expire(String key,int seconds){
//		this.cache.expire(key, time, timeUnit);
		redisClient.expire(key, seconds);
	}
	
	public Set<String> keys(String key){
		Set<String> keys = redisClient.keys(key);
		return keys;
	}
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加1
	 * @param key
	 * @return
	 */
	public Long incr(String key){
		return redisClient.incr(key);
	}
	
	public Long incrBy(String key,long i){
		return redisClient.incrBy(key,i);
	}

}
