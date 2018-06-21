package com.yinhai.ec.system.service;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;

/**
* @package com.yinhai.ec.system.service
* <p>Title: AppCodeService.java</p>
* <p>Description: 码表管理service</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年4月11日 上午10:05:06
* @version 1.0
 */
public interface AppCodeService extends BaseService{

	/**
	  * @package com.yinhai.ec.system.service
	  * @method saveCode 方法 
	  * @describe <p>方法说明:保存新增或编辑的码表</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月11日 上午10:51:14
	 */
	void saveCode(PageParam param, ResultBean bean, UserDomain loginUser) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method deleteCode 方法 
	  * @describe <p>方法说明:删除码表</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月11日 上午11:33:48
	 */
	void deleteCode(PageParam pageParam, ResultBean bean) throws Exception;
	
}
