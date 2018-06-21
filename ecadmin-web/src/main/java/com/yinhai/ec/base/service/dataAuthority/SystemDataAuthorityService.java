package com.yinhai.ec.base.service.dataAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;

/**
* @package com.yinhai.ec.base.service.dataAuthority
* <p>Title: SystemDataAuthorityService.java</p>
* <p>Description: 框架数据权限 相关API</p>
* @author cjh
* @date 2016年4月25日 下午2:22:11
* @version 1.0
 */
public interface SystemDataAuthorityService extends BaseService{
	
	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method getSystemDbTables 方法 
	  * @describe <p>方法说明: 查询系统相关数据表信息</p>
	  * 返回值字段描述</br>
	  * db_name 所属数据库标识</br>
	  * table_name 表英文名</br>
	  * table_comment 表中文名
	  * @return List<Map<String,String>> 
	  * @author cjh
	  * @date 2016年4月25日 下午2:36:22
	 */
	List<Map<String, String>> getSystemDbTables();
	
	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method getAppDbTables 方法 
	  * @describe <p>方法说明: 查询业务相关数据表信息</p>
	  * 返回值字段描述</br>
	  * db_name 所属数据库标识</br>
	  * table_name 表英文名</br>
	  * table_comment 表中文名
	  * @return List<Map<String,String>> 
	  * @author cjh
	  * @date 2016年4月25日 下午2:36:22
	 */
	List<Map<String, String>> getAppDbTables();
	
	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method getDbName 方法 
	  * @describe <p>方法说明: 获取数据库名称</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年4月25日 下午4:59:48
	 */
	String getDbName();
	
	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method getDataAuthorityByRoleIdAndDataType 方法 
	  * @describe <p>方法说明:根据role_id 以及 数据权限类型 获取角色的数据权限集合</p>
	  * @return List<Map<String,String>> 
	  * @author cjh
	  * @date 2016年4月29日 下午3:05:01
	 */
	List<Map<String, String>> getDataAuthorityByRoleIdAndDataType(Integer role_id,String type);
	
	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method getDataAuthoritysByRoleId 方法 
	  * @describe <p>方法说明:根据roleid 获取角色的4个数据权限集合 是不可重复的set<String>集合</p>
	  * @return Map<String,Set<String>> 
	  * @author cjh
	  * @date 2016年5月3日 上午9:58:31
	 */
	Map<String, Set<String>> getDataAuthoritysByRoleId(Integer role_id);

	/**
	  * @package com.yinhai.ec.base.service.dataAuthority
	  * @method saveRoleDataAuthority 方法 
	  * @describe <p>方法说明:保存角色相关数据权限</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月29日 下午4:07:09
	 */
	void saveRoleDataAuthority(PageParam pageParam, ResultBean bean) throws Exception;
}
