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
public class RedisObjAdapter {
	Logger log = Logger.getLogger(getClass());
	private RedisClient redisClient;
	
	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}


	/**
	 * 设置过期时间
	 * @param key 键
	 * @param timeout 秒
	 * @return
	 */
	public void expire(String key,int timeout){
		redisClient.expire(key, timeout);
	}

	/**
	 * 设置缓存值
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void set(String key, T value) {
		redisClient.setObject(key, value);

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
	 *            过期时间，秒
	 */
	public <T> void set(String key, T value, long exp) {
		redisClient.setObject(key, value, exp);
	}
	
	public void setObje(String key, Object value) {
		redisClient.setObject(key, value);
	}
	
	public Object getObje(String key) {
		return redisClient.getObj(key);
	}

	/**
	 * 根据键值删除缓存数据
	 * @param <T>
	 * @param key
	 */
	public <T> void del(String key) {
		redisClient.delObj(key);
	}

	/**
	 * 将一个或多个值value插入到列表key的表头。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, Object value) {
		return redisClient.inObj(key, value);
	}

	/**
	 * 移除并返回列表key的头元素。
	 * @param key
	 * @return
	 */
	public Object pop(String key) {
		return redisClient.outObj(key);
	}
	
	/**
	 * 更新List中最后一个value
	 * @param key
	 * @param value
	 */
	public void setNew(String key,Object value){
		redisClient.setNew(key,value);
	}
	
	/**
	 * 获取List中的最后一项
	 * @param key
	 * @return
	 */
	public Object getLast(String key){
		return redisClient.getLast(key);
	}
	
	/**
	  * @package com.yinhai.analysis.redis
	  * @method setLast 方法 
	  * @describe <p>方法说明:设置list最后一项</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-7
	 */
	public void setLast(String key,Object object){
		redisClient.setIndexValueObj(key, redisClient.length(key)-1, object);
	}
	
	/**
	 * List长度
	 * @param key
	 * @return
	 */
	public Long size(String key){
		return redisClient.length(key);
	}
	
	/**
	 *  将一个或多个值value插入到列表key的表尾。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, Object value) {
		return redisClient.inObj(key, value);
	}

	/**
	 * 移除并返回列表key的尾元素。
	 * 
	 * @param key
	 * @return
	 */
	public Object out(String key) {
		return redisClient.outObj(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		return redisClient.lengthObj(key);
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
	public List<Object> range(String key, int start, int end) {
		return redisClient.rangeObj(key, start, end);
	}

	/**
	 * 移除列表中与参数
	 * 根据参数count的值，移除列表中与参数value相等的元素。
	 * @param key
	 * @param i
	 * @param value
	 */
	public void remove(String key, long i, String value) {
		redisClient.removeObj(key, i, value);
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
	public Object index(String key, long index) {
		return redisClient.indexObj(key, index);
	}

	/**
	 * 重置指定元素值
	 * 将列表key下标为index的元素的值甚至为value。<br>
	 * @param key
	 * @param index
	 * @param value
	 */
	public void setIndexValue(String key, long index, String value) {
		redisClient.setIndexValueObj(key, index, value);
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
		redisClient.trimObj(key, start, end);
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
	public Long zadd(String key,Object value,double score){
		return redisClient.zaddObj(key, value, score);
	}
	
	
	/**
	 * 获取从start到end的所有List中的元素
	 * 
	 * @param key
	 * @param start
	 *            负数表示倒序
	 * @param end
	 * @return
	 */
	public List getList(String key, Integer start, Integer end) {
		return redisClient.rangeObj(key, start, end);
	}
	
	/**
	 * 获取从start到end的所有List中的元素
	 * 
	 * @param key
	 * @param start
	 *            负数表示倒序
	 * @param end
	 * @return
	 */
	public List getList(String key, Integer start, Long end) {
		return redisClient.rangeObj(key, start, end.intValue());
	}
	/**
	 * 返回有序集key中，score值在min和max之间(默认包括score值等于min或max)的成员。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zcount(String key,double min,double max){
		return redisClient.zcountObj(key, min, max);
	}
	
	/**
	 * 返回有序集key中，指定区间内的成员。其中成员按score值递增(从小到大)来排序。
	 * 具有相同score值的成员按字典序来排列。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set zrange(String key,long start,long end){
		return redisClient.zrangeObj(key, start, end);
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 * 有序集成员按 score 值递增(从小到大)次序排列。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set zrangeByScore(String key,double min,double max){
		return redisClient.zrangeByScoreObj(key,min,max);
	}
	
	public Set zrangeByScoreWithScores(String key,double min,double max){
		return redisClient.zrangeByScoreWithScoresObj(key, min, max);
	}
	
	public Set zrangeWithScores(String key,Long start,Long end){
		return redisClient.zrangeWithScoresObj(key,start,end);
	}
	
	public void emoveByPrefix(String key){
		try{
			Set<String> keys = redisClient.keys(key);
			if(keys!=null&&keys.size()>0){
				for (String k : keys) {
					redisClient.delObj(k);
				}
			}
		}catch(Exception e){
			log.error("批量删除：\""+key+" \" key不存在",e);
		}
	}
//	public long getExpire(String key){
//		return this.objcache.getExpire(key);
//	}
	
	public Set<String> keys(String key){
		Set<String> keys = redisClient.keys(key);
		return keys;
	}
	
	public boolean exists(String key){
		return redisClient.exists(key);
	}
	
	/**
	 * 计数器 放入KEY 若没有KEY则创建KEY并加1
	 * @param key
	 * @return
	 */
	public Long incr(String key){
		return redisClient.incrObj(key);
	}
	
	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。
	 * @param key
	 * @param value
	 * @return key不存在设置成功返回true，反之key存在，返回false
	 */
	public <T> boolean setNX(String key,T value){
		return redisClient.setNXObj(key, value);
	}
	
	/**
	 * 将一个value从左插入到列表key的表头。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lpush(String key, Object value) {
		return redisClient.pushObj(key, value);
	}
	
	/**
	 * 删除队列中的值
	 * @param key 队列名
	 * @param value 待删除值
	 * @param i 删除数量
	 * @return
	 */
	public Long lrem(String key,Object value,int i){
		return redisClient.removeObj(key, i, value);
	}
}
