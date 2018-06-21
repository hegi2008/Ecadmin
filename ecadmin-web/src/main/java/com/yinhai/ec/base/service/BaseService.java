package com.yinhai.ec.base.service;

import java.sql.Timestamp;

import com.yinhai.ec.base.util.PageParam;
  
public abstract interface BaseService {
	/**
	  * @package com.yinhai.ec.base.service
	  * @method getSystemTimestamp 方法 
	  * @describe <p>方法说明:获取系统时间</p>
	  * @return Timestamp 
	  * @author cjh
	  * @date 2015-12-31
	 */
	Timestamp getSystemTimestamp() throws Exception;
	
	/**
	  * @package com.yinhai.ec.base.service
	  * @method queryForPageWithDefault 方法 
	  * @describe <p>方法说明:封装mybatis分页</p>
	  * @return PageParam 
	  * @author cjh
	  * @date 2016-1-12
	 */
	void queryForPageWithDefault(String mapper,PageParam pageParam) throws Exception;
}
 