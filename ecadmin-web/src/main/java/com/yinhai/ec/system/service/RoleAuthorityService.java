package com.yinhai.ec.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserRolesDomain;

/**
* @package com.yinhai.ec.system.service
* <p>Title: RoleAuthorityService.java</p>
* <p>Description: 角色权限管理service</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年1月26日 下午1:59:14
* @version 1.0
 */
public interface RoleAuthorityService extends BaseService{

	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryRolesList 方法 
	  * @describe <p>方法说明:查询角色列表</p>
	  * @return PageParam 
	  * @author cjh
	  * @date 2016年1月26日 下午2:07:49
	 */
	void queryRolesList(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method querySingleRole 方法 
	  * @describe <p>方法说明:根据role_id获取单个role</p>
	  * @return UserRolesDomain 
	  * @author cjh
	  * @date 2016年1月26日 下午2:54:59
	 */
	UserRolesDomain querySingleRole(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryMenusList 方法 
	  * @describe <p>方法说明:查询菜单list</p>
	  * @return List<Map<String,Object>> 
	  * @author cjh
	  * @date 2016年1月26日 下午3:39:31
	 */
	List<Map<String, Object>> queryMenusList(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryMenuAuthorityIdsByRoleId 方法 
	  * @describe <p>方法说明:获取角色权限menu ids</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年1月26日 下午5:23:35
	 */
	String queryMenuAuthorityIdsByRoleId(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method saveRoleAuthority 方法 
	  * @describe <p>方法说明:保存角色权限</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016年1月27日 上午11:39:15
	 */
	void saveRoleAuthority(PageParam pageParam, ResultBean bean, Map<RequestMappingInfo, HandlerMethod> map) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method queryAuthorityListByRoleId 方法 
	  * @describe <p>方法说明:根据role_id查询角色的权限列表</p>
	  * @return List<Map<String,Object>> 
	  * @author cjh
	  * @date 2016年1月29日 下午3:52:51
	 */
	List<Map<String, Object>> queryAuthorityListByRoleId(PageParam pageParam) throws Exception;

	/**
	  * @package com.yinhai.ec.system.service
	  * @method updateAuthorityOne 方法 
	  * @describe <p>方法说明:根据role_id menu_id修改权限状态</p>
	  * @return ResultBean 
	  * @author cjh
	 * @param bean 
	  * @date 2016年1月29日 下午5:04:23
	 */
	void updateAuthorityOne(PageParam pageParam, ResultBean bean) throws Exception;
	
}
