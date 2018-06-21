package com.yinhai.ec.sso;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.service.dataAuthority.SystemDataAuthorityService;
import com.yinhai.ec.base.service.user.UserService;
import com.yinhai.ec.base.shiro.MyRealmHelper;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;

/**
* @package com.yinhai.ec.sso
* <p>Title: ShiroCasRealm.java</p>
* <p>Description: ShiroCasRealm</p>
* @author cjh
* @date 2016年9月6日 下午1:16:10
* @version 1.0
 */
public class ShiroCasRealm extends CasRealm{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private SystemDataAuthorityService dataAuthority;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		String username = principal.getPrimaryPrincipal()+"";
		if(!StringUtils.hasText(username)){
			return null;
		}
		UserDomain user = userService.findUserByUsername(username);
		session.setAttribute(HYConst.SESSION_USER, user);
		try {
			List<UserRolesDomain> roles = userService.getUserRolesByUserId(user.getUserId());
			if (roles != null && roles.size() > 0) {
				session.setAttribute(HYConst.USER_ROLES, new Vector<UserRolesDomain>(roles));
			}
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (int i = 0; i < roles.size(); i++) {
				UserRolesDomain role = roles.get(i);
				// 添加角色字符串
				info.addRole(role.getRoleString());
				// 添加角色数据权限
				ConcurrentMap<String, Object> map = MyRealmHelper.createUserRoleDataAuthority(role,dataAuthority);
				session.setAttribute(HYConst.PERMISSIONS_DATAAUTHORITYS_MAP, map);
			}
			String roleString = MyRealmHelper.getRoleString(roles);
			List<Map<String, Object>> permissionUrls = userService
					.getUserPermissionUrlsByRoleString(roleString);
			for (int i = 0; i < permissionUrls.size(); i++) {
				Map<String, Object> map = permissionUrls.get(i);
				if (map.get("menu_url") != null && !"".equals(map.get("menu_url").toString())) {
					String url = map.get("menu_url").toString();
					if (url.startsWith("/")) {
						url = url.substring(1, url.length());
					}
					url = url.replaceAll("/", ":");
					String[] strings = url.split(":");
					if (HYConst.PATH_DEFAULT.equals(strings[strings.length - 1])) {
						url = url.substring(0, url.lastIndexOf(":"));
					}
					Object role_string = map.get("role_string");

					String role_create = map.get("role_create").toString();
					String role_query = map.get("role_query").toString();
					String role_update = map.get("role_update").toString();
					String role_delete = map.get("role_delete").toString();
					if (HYConst.STATUS_YES.equals(Integer.valueOf(role_create))
							&& HYConst.STATUS_YES.equals(Integer.valueOf(role_query))
							&& HYConst.STATUS_YES.equals(Integer.valueOf(role_update))
							&& HYConst.STATUS_YES.equals(Integer.valueOf(role_delete))) {
						info.addStringPermission(url.concat(":*"));
					} else {
						info.addStringPermission(url.concat(":default"));
						if (HYConst.STATUS_YES.equals(Integer.valueOf(role_create))) {
							MyRealmHelper.addStringPermission(info, role_string, url, "c");
						}
						if (HYConst.STATUS_YES.equals(Integer.valueOf(role_query))) {
							MyRealmHelper.addStringPermission(info, role_string, url, "q");
						}
						if (HYConst.STATUS_YES.equals(Integer.valueOf(role_update))) {
							MyRealmHelper.addStringPermission(info, role_string, url, "u");
						}
						if (HYConst.STATUS_YES.equals(Integer.valueOf(role_delete))) {
							MyRealmHelper.addStringPermission(info, role_string, url, "d");
						}
					}
				}
			}
			return info;
		} catch (Exception e) {
			logger.error("发生异常,在获取用户权限信息的时候 {}", e);
		}
		return null;
	}
}
