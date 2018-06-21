package com.yinhai.ec.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.yinhai.ec.base.exception.BaseParamException;
import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.MenuAuthDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
import com.yinhai.ec.system.service.RoleAuthorityService;

@Service
@Transactional
public class RoleAuthorityServiceImpl extends BaseServiceImpl implements RoleAuthorityService{

	@Override
	public void queryRolesList(PageParam pageParam) throws Exception {
		List<Map<String,Object>> list = sqlSession.selectList("hy.role.roleAuthority.queryRolesList", pageParam);
		pageParam.setList(list);
		pageParam.setTotal(Long.valueOf(list.size()));
	}

	@Override
	public UserRolesDomain querySingleRole(PageParam pageParam) throws Exception {
		if (pageParam.get("role_id") == null || "".equals(pageParam.get("role_id").toString())) {
			throw new BaseParamException("role_id", "不能为空");
		}
		return sqlSession.selectOne("hy.role.roleAuthority.selectRoleByPrimaryKey", pageParam);
	}

	@Override
	public List<Map<String, Object>> queryMenusList(PageParam pageParam) throws Exception {
		
		return sqlSession.selectList("hy.role.roleAuthority.queryMenuList", pageParam);
	}

	@Override
	public String queryMenuAuthorityIdsByRoleId(PageParam pageParam) throws Exception {
		if (pageParam.get("role_id") == null || "".equals(pageParam.get("role_id").toString())) {
			throw new BaseParamException("role_id", "不能为空");
		}
		return sqlSession.selectOne("hy.role.roleAuthority.queryMenuAuthorityIdsByRoleId", pageParam);
	}

	@Override
	public void saveRoleAuthority(PageParam pageParam, ResultBean bean,
			Map<RequestMappingInfo, HandlerMethod> map) throws Exception {
		if (pageParam.get("role_id") == null || "".equals(pageParam.get("role_id").toString())) {
			bean.setError_msg("未获取到需要更新的角色信息,请关闭弹窗重新操作!");
		}
		if (pageParam.get("menu_ids") == null || "".equals(pageParam.get("menu_ids").toString()) ||
				pageParam.get("parent_ids") == null || "".equals(pageParam.get("parent_ids").toString())) {
			bean.setError_msg("未获取到授权信息,请刷新页面重新操作!");
		}
		if (StringUtils.isEmpty(bean.getError_msg())) {
			Integer role_id = Integer.valueOf((String)pageParam.get("role_id"));
			// 删除之前的所有权限
			sqlSession.delete("hy.role.roleAuthority.deleteRoleAuthByRoleId", role_id);
			
			String menu_ids = pageParam.getString("menu_ids");
			String parent_ids = pageParam.getString("parent_ids");
			String menu_urls = pageParam.getString("menu_urls");
			List<MenuAuthDomain> auths = new ArrayList<MenuAuthDomain>();
			// 将父菜单添加进list
			String[] pp = parent_ids.split(",");
			Set<String> set = new HashSet<String>(Arrays.asList(pp));// 去除重复
			for (String p : set) {
				MenuAuthDomain domain = new MenuAuthDomain();
				domain.setRoleId(role_id);
				domain.setMenuId(Integer.valueOf(p));
				domain.setIsParent(HYConst.STATUS_YES);
				auths.add(domain);
			}
			// 子菜单 带URL的 并且要进行权限方法分析的权限加进list
			String[] ids = menu_ids.split(",");
			String[] paths = menu_urls.split(",");
			//Set<String> idsset = new HashSet<String>(Arrays.asList(ids));// 去除重复
			//Set<String> pathsset = new HashSet<String>(Arrays.asList(paths));// 去除重复
			List<Class<?>> classList = new LinkedList<Class<?>>();
			for (int i = 0; i < paths.length; i++) {
				String path = paths[i];
				if("null".equals(path) || StringUtils.isEmpty(path) || path.startsWith("druid")){
					classList.add(null);
					continue;
				}
				for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {  
		            RequestMappingInfo info = m.getKey();  
		            HandlerMethod method = m.getValue();  
		            String url =info.getPatternsCondition().toString().substring(2, info.getPatternsCondition().toString().length()-1);
		            Class<?> class1 =  method.getMethod().getDeclaringClass();
		            if (url.equals(path)) {
	            		classList.add(class1);
	            		break;
	            	}
		        }
			}
			
			int i = 0;
			for (String id : ids) {
				MenuAuthDomain domain = new MenuAuthDomain();
				domain.setMenuId(Integer.valueOf(id));
				domain.setRoleId(role_id);
				domain.setIsParent(HYConst.STATUS_NO);
				StringBuilder roleString = new StringBuilder();
				if(i == classList.size() -1){
					auths.add(domain);
					continue;
				}
				Class<?> roleClass = classList.get(i);
				if (roleClass == null) {
					auths.add(domain);
					continue;
				}
				for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
					RequestMappingInfo info = m.getKey();  
		            HandlerMethod method = m.getValue();  
		            String url = info.getPatternsCondition().toString().substring(2, info.getPatternsCondition().toString().length()-1);
		            Class<?> class1 = method.getMethod().getDeclaringClass();
		            if(roleClass.getName().equals(class1.getName())){
		            	url = url.replaceAll("/", ":");
		            	roleString.append(url);
		            	roleString.append(",");
		            }
				}
				if (!StringUtils.isEmpty(roleString)) {
					String ss = roleString.toString();
					ss = ss.substring(0, ss.length()-1);
					domain.setRoleString(ss);
				}
				auths.add(domain);
				i++;
			}
			
			if (auths.size() > 0) {
				sqlSession.insert("hy.role.roleAuthority.inserRoleAuthBatch", auths);
			}
			bean.setSuccess_msg("授权成功");
		}else{
			bean.setError(true);
		}
	}

	@Override
	public List<Map<String, Object>> queryAuthorityListByRoleId(PageParam pageParam) throws Exception {
		if (pageParam.get("role_id") == null || "".equals(pageParam.get("role_id").toString())) {
			throw new BaseParamException("role_id", "不能为空");
		}
		return sqlSession.selectList("hy.role.roleAuthority.queryAuthorityListByRoleId", pageParam);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateAuthorityOne(PageParam pageParam,ResultBean bean) throws Exception {
		if (pageParam.get("role_id") == null || "".equals(pageParam.get("role_id").toString())) {
			throw new BaseParamException("role_id", "不能为空");
		}
		if (pageParam.get("menu_id") == null || "".equals(pageParam.get("menu_id").toString())) {
			throw new BaseParamException("menu_id", "不能为空");
		}
		if (pageParam.get("status") == null || "".equals(pageParam.get("status").toString())) {
			throw new BaseParamException("将要更新的状态不能为空");
		}
		if (pageParam.get("field") == null || "".equals(pageParam.get("field").toString())) {
			throw new BaseParamException("需要更新的字段不能为空");
		}
		Map authDomain = new HashMap();
		authDomain.put("role_id", Integer.valueOf(pageParam.getString("role_id")));
		authDomain.put("menu_id", Integer.valueOf(pageParam.getString("menu_id")));
		Integer status = Integer.valueOf(pageParam.getString("status"));
		authDomain.put("status", status);
		authDomain.put("field", pageParam.getString("field"));
		int count = sqlSession.update("hy.role.roleAuthority.updateMenuAuthorityOne", authDomain);
		if (count != 1) {
			throw new BaseUpdateException(count, "更新权限时");
		}
	}
	
}
