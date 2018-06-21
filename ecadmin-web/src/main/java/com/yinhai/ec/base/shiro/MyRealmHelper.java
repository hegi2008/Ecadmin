package com.yinhai.ec.base.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.service.dataAuthority.SystemDataAuthorityService;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.UserRolesDomain;

public class MyRealmHelper {
	/**
	  * @package com.yinhai.ec.base.shiro.realm
	  * @method addUserRoleDataAuthority 方法 
	  * @describe <p>方法说明:添加角色数据权限</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年5月3日 上午10:15:22
	 */
	@SuppressWarnings({ "unchecked" })
	public static ConcurrentMap<String, Object> createUserRoleDataAuthority(UserRolesDomain role, SystemDataAuthorityService dataAuthority) {
		ConcurrentMap<String, Object> dataauthoritys_map = new ConcurrentHashMap<String,Object>();
		Map<String, Set<String>> permissions = dataAuthority.getDataAuthoritysByRoleId(role.getRoleId());
		if(permissions.containsKey(HYConst.DATA_AUTHORITY_INSERT) && permissions.get(HYConst.DATA_AUTHORITY_INSERT).size() > 0){
			if(dataauthoritys_map.get(HYConst.DATA_AUTHORITY_INSERT) != null){
				Set<String> strings = (Set<String>) dataauthoritys_map.get(HYConst.DATA_AUTHORITY_INSERT);
				strings.addAll(permissions.get(HYConst.DATA_AUTHORITY_INSERT));
			}else{
				dataauthoritys_map.put(HYConst.DATA_AUTHORITY_INSERT, permissions.get(HYConst.DATA_AUTHORITY_INSERT));
			}
		}
		if(permissions.containsKey(HYConst.DATA_AUTHORITY_DELETE) && permissions.get(HYConst.DATA_AUTHORITY_DELETE).size() > 0){
			if(dataauthoritys_map.get(HYConst.DATA_AUTHORITY_DELETE) != null){
				Set<String> strings = (Set<String>) dataauthoritys_map.get(HYConst.DATA_AUTHORITY_DELETE);
				strings.addAll(permissions.get(HYConst.DATA_AUTHORITY_DELETE));
			}else{
				dataauthoritys_map.put(HYConst.DATA_AUTHORITY_DELETE, permissions.get(HYConst.DATA_AUTHORITY_DELETE));
			}
		}
		if(permissions.containsKey(HYConst.DATA_AUTHORITY_SELECT) && permissions.get(HYConst.DATA_AUTHORITY_SELECT).size() > 0){
			if(dataauthoritys_map.get(HYConst.DATA_AUTHORITY_SELECT) != null){
				Set<String> strings = (Set<String>) dataauthoritys_map.get(HYConst.DATA_AUTHORITY_SELECT);
				strings.addAll(permissions.get(HYConst.DATA_AUTHORITY_SELECT));
			}else{
				dataauthoritys_map.put(HYConst.DATA_AUTHORITY_SELECT, permissions.get(HYConst.DATA_AUTHORITY_SELECT));
			}
		}
		if(permissions.containsKey(HYConst.DATA_AUTHORITY_UPDATE) && permissions.get(HYConst.DATA_AUTHORITY_UPDATE).size() > 0){
			if(dataauthoritys_map.get(HYConst.DATA_AUTHORITY_UPDATE) != null){
				Set<String> strings = (Set<String>) dataauthoritys_map.get(HYConst.DATA_AUTHORITY_UPDATE);
				strings.addAll(permissions.get(HYConst.DATA_AUTHORITY_UPDATE));
			}else{
				dataauthoritys_map.put(HYConst.DATA_AUTHORITY_UPDATE, permissions.get(HYConst.DATA_AUTHORITY_UPDATE));
			}
		}
		
		return dataauthoritys_map;
	}

	/**
	  * @package com.yinhai.ec.base.shiro.realm
	  * @method addStringPermission 方法 
	  * @describe <p>方法说明:根据标志,添加权限</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年2月1日 上午11:49:08
	 */
	public static void addStringPermission(SimpleAuthorizationInfo info, Object role_string, String url,String flag) {
		if (StringUtils.isEmpty(role_string)) {
			info.addStringPermission(url.concat(":*"));
		}else{
			
			String[] array = role_string.toString().split(",");
			Set<String> urls = new HashSet<String>();
			for (int i = 0; i < array.length; i++) {
				String realPath = array[i];
				String path = array[i].substring(array[i].lastIndexOf(":")+1, array[i].length());
				if ("c".equals(flag)) {
					// 增 
					if(path.startsWith("create") || path.startsWith("new") 
							|| path.startsWith("save") || path.startsWith("add")){
						urls.add(realPath);
					}
				}else if("d".equals(flag)){
					// 删
					if(path.startsWith("delete") || path.startsWith("remove")){
						urls.add(realPath);
					}
					
				}else if("q".equals(flag)){
					// 查
					if(path.startsWith("query") || path.startsWith("view") 
							|| path.startsWith("select") || path.startsWith("get")
							|| path.startsWith("got")){
						urls.add(realPath);
					}
				}else if("u".equals(flag)){
					// 改
					if(path.startsWith("update") || path.startsWith("edit") 
							|| path.startsWith("fix")){
						urls.add(realPath);
					}
				}
			}
			if (!urls.isEmpty() && urls.size() > 0) {
				info.addStringPermissions(urls);
			}
		}
	}

	public static String getRoleString(List<UserRolesDomain> roles) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < roles.size(); i++) {
			UserRolesDomain role = roles.get(i);
			builder.append(role.getRoleId());
			if(i != roles.size()-1){
				builder.append(",");
			}
		}
		return builder.toString();
	}
}
