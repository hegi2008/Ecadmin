package com.yinhai.ec.base.service.user;  

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
  
/**
* @package com.yinhai.ec.base.service.user
* <p>Title: UserService.java</p>
* <p>Description: HY用户service</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2015-12-31 上午10:02:12
* @version 1.0
 */
public interface UserService extends BaseService{
	
	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method getUserByLogin 方法 
	  * @describe <p>方法说明:判断用户登陆</p>
	  * @return UserDomain 
	  * @author cjh
	  * @date 2015-12-31
	 */
	UserDomain getUserByLogin(String username,String password) throws AuthenticationException;

	public UserDomain findUserByUsername(String username);
	
	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method getUserRolesByUserId 方法 
	  * @describe <p>方法说明:根据用户ID获取用户角色roles</p>
	  * @return List<UserRolesDomain> 
	  * @author cjh
	  * @date 2016-1-4
	 */
	List<UserRolesDomain> getUserRolesByUserId(Integer userId) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method getUserPermissionUrlsByRoleString 方法 
	  * @describe <p>方法说明:根据用户权限字符串获取用户可访问的权限URL列表</p>
	  * @return List<Map<String,Object>> 
	  * @author cjh
	  * @date 2016-1-4
	 */
	List<Map<String, Object>> getUserPermissionUrlsByRoleString(
			String roleString) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method updateUserInfo 方法 
	  * @describe <p>方法说明:修改个人信息</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016年2月24日 下午4:38:19
	 */
	void updateUserInfo(UserDomain user, ResultBean bean) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method updateUserPassword 方法 
	  * @describe <p>方法说明:</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016年2月25日 上午10:43:55
	 */
	void updateUserPassword(PageParam pageParam, ResultBean bean) throws Exception;
	
	/**
	  * @package com.yinhai.ec.base.service.user
	  * @method UpdateUserPwdWrongTime 方法 
	  * @describe <p>方法说明:更新密码输入错误次数</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年3月30日 下午4:09:54
	 */
	void UpdateUserPwdWrongTime(String username, Integer statusNo) throws Exception;
}
 