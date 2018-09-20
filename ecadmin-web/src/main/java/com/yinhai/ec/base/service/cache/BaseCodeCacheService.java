package com.yinhai.ec.base.service.cache;

import java.util.List;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.common.domain.AppCodeDomain;

/**
* @package com.yinhai.ec.base.service.cache
* <p>Title: BaseCacheService.java</p>
* <p>Description: 码表缓存service</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 贵州医美达</p>
* @author cjh
* @date 2016年2月1日 下午4:45:32
* @version 1.0
 */
public interface BaseCodeCacheService extends BaseService{
	/**
	  * @package com.yinhai.ec.base.service.cache
	  * @method getAppCodeByCodeString 方法 
	  * @describe <p>方法说明:缓存appcode码表</p>
	  * @return List<AppCodeDomain> 
	  * @author cjh
	  * @date 2016年2月2日 上午11:40:57
	 */
	List<AppCodeDomain> getAppCodeByCodeString(String code_string) throws Exception;
	
	/**
	  * @package com.yinhai.ec.base.service.cache
	  * @method getGroupCodeString 方法 
	  * @describe <p>方法说明: 获取所有codeString的分组</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年4月11日 下午2:04:08
	 */
	String getGroupCodeString() throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.cache
	  * @method getAppCodeByDb 方法 
	  * @describe <p>方法说明:直接从数据库获取码表</p>
	  * @return List<AppCodeDomain> 
	  * @author cjh
	  * @date 2016年4月11日 下午2:03:27
	 */
	List<AppCodeDomain> getAppCodeByDb(String code_string);
}
