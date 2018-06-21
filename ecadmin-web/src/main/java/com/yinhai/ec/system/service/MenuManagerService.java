package com.yinhai.ec.system.service;  

import java.util.Map;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
  
public interface MenuManagerService extends BaseService{

	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryMenuList 方法 
	  * @describe <p>方法说明:获取菜单list treegrid需要的</p>
	  * @return PageParam 
	  * @author cjh
	  * @date 2016-1-18
	 */
	void queryMenuList(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method saveMenu 方法 
	  * @describe <p>方法说明:保存/更新菜单</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016-1-19
	 */
	void saveMenu(Map<String, Object> param, UserDomain user,
			ResultBean bean) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method deleteMenu 方法 
	  * @describe <p>方法说明:删除菜单</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016-1-20
	 */
	void deleteMenu(PageParam pageParam, ResultBean bean) throws Exception;
	
}
 