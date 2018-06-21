package com.yinhai.ec.system.service;  

import java.util.Map;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
  
public interface RoleManagerService extends BaseService{
	/**
	  * 
	  * @package com.yinhai.ec.system.service
	  * @method queryRoleList 方法 
	  * @describe <p>方法说明:查询角色list</p>
	  * @return ResultBean 
	  * @date 2016年1月26日 下午4:34:32
	 */
	void queryUserRoleList(PageParam pageParam) throws Exception;
	/**
	 * 
	  * @package com.yinhai.ec.system.service
	  * @method saveRole 方法 
	  * @describe <p>方法说明:保存角色信息</p>
	  * @return ResultBean 
	  * @date 2016年1月26日 下午4:36:04
	 */
	void saveRole(PageParam pageParam, ResultBean bean, UserDomain loginUser) throws Exception;
	/**
	 * 
	  * @package com.yinhai.ec.system.service
	  * @method updateRole 方法 
	  * @describe <p>方法说明:编辑角色</p>
	  * @return ResultBean 
	  * @date 2016年1月26日 下午4:35:27
	 */
	void updateRole(PageParam pageParam, ResultBean bean) throws Exception;
	/**
	 * 
	  * @package com.yinhai.ec.system.service
	  * @method deleteRole 方法 
	  * @describe <p>方法说明:删除角色</p>
	  * @return ResultBean 
	  * @date 2016年1月27日 下午2:14:00
	 */
	void deleteRole(Map<?, ?> param, ResultBean bean) throws Exception;
	/**
	  * @package com.yinhai.ec.system.service
	  * @method querySingleRole 方法 
	  * @describe <p>方法说明:根据roleid查询单个role</p>
	  * @return UserRolesDomain 
	  * @author cjh
	  * @date 2016年2月22日 上午10:06:58
	 */
	UserRolesDomain querySingleRole(Integer role_id) throws Exception;
	/**
	  * @package com.yinhai.ec.system.service
	  * @method updateRoleUser 方法 
	  * @describe <p>方法说明:添加或者删除角色人员</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016年2月22日 下午1:57:44
	 */
	void updateRoleUser(PageParam pageParam, ResultBean bean) throws Exception;
	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryFullRole 方法 
	  * @describe <p>方法说明:查询role全部信息 包括部门信息</p>
	  * @return Map<String,Object> 
	  * @author cjh
	  * @date 2016年4月29日 上午9:24:14
	 */
	Map<String, Object> queryFullRole(Integer role_id);
	
}
 