package com.yinhai.ec.base.service.dataAuthority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yinhai.ec.base.service.dataAuthority.SystemDataAuthorityService;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.util.StringUtils;

@Service
@Transactional
public class SystemDataAuthorityServiceImpl extends BaseServiceImpl implements SystemDataAuthorityService {

	@Override
	public List<Map<String, String>> getSystemDbTables() {
		String db_name = getDbName();
		Map<String, String> param = new HashMap<String,String>();
		param.put("db_name", db_name);
		return sqlSession.selectList("hy.system.dataAuthority.getSystemDbTables", param);
	}

	@Override
	public List<Map<String, String>> getAppDbTables() {
		String db_name = getDbName();
		Map<String, String> param = new HashMap<String,String>();
		param.put("db_name", db_name);
		return sqlSession.selectList("hy.system.dataAuthority.getAppDbTables", param);
	}

	@Override
	public String getDbName() {
		return sqlSession.selectOne("hy.system.dataAuthority.getDbName");
	}

	@Override
	public List<Map<String, String>> getDataAuthorityByRoleIdAndDataType(Integer role_id, String type) {
		Assert.notNull(role_id);
		Assert.notNull(type);
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("role_id", role_id);
		param.put("type", type);
		return sqlSession.selectList("hy.system.dataAuthority.getDataAuthorityByRoleIdAndDataType", param);
	}

	@Override
	public void saveRoleDataAuthority(PageParam pageParam, ResultBean bean) throws Exception {
		bean.setError(true);
		bean.setError_msg("保存失败");
		Assert.notNull(pageParam.get("role_id"));
		Assert.notNull(pageParam.get("type"));
		Object systems = pageParam.get("systems");
		Object apps = pageParam.get("apps");
		String table_schema = getDbName();
		List<Map<String,String>> all = new ArrayList<Map<String,String>>();
		List<Map<String,String>> l1 = new ArrayList<Map<String,String>>();
		List<Map<String,String>> l2 = new ArrayList<Map<String,String>>();
		if (!StringUtils.isEmpty(systems)) {
			String[] sysAuthoritys = systems.toString().split(",");
			for (int i = 0; i < sysAuthoritys.length; i++) {
				String sysAuthority = sysAuthoritys[i];
				Map<String,String> map = new HashMap<String,String>();
				map.put("role_id", pageParam.get("role_id").toString());
				map.put("table_name", sysAuthority);
				map.put("table_schema", table_schema);
				l1.add(map);
			}
			
		}
		if (!StringUtils.isEmpty(apps)) {
			String[] appAuthoritys = apps.toString().split(",");
			for (int i = 0; i < appAuthoritys.length; i++) {
				String appAuthority = appAuthoritys[i];
				Map<String,String> map = new HashMap<String,String>();
				map.put("role_id", pageParam.get("role_id").toString());
				map.put("table_name", appAuthority);
				map.put("table_schema", table_schema);
				l2.add(map);
			}
		}
		
		if (l1.size() > 0) {
			all.addAll(l1);
		}
		if (l2.size() > 0) {
			all.addAll(l2);
		}
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("type", pageParam.get("type"));
		param.put("role_id", pageParam.get("role_id"));
		sqlSession.delete("hy.system.dataAuthority.deleteDataAuthoritysByRoleId", param);
		if(all.size() > 0){
			param.put("list", all);
			sqlSession.insert("hy.system.dataAuthority.inserRoleDataAuthorityBatch",param);
		}
		
		bean.setError(false);
		bean.setSuccess_msg("保存成功");
	}

	@Override
	public Map<String, Set<String>> getDataAuthoritysByRoleId(Integer role_id) {
		Assert.notNull(role_id);
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("role_id", role_id);
		// 增加
		addPermissionsByType(result,HYConst.DATE_AUTHORITY_TYPE_CREATE,HYConst.DATA_AUTHORITY_INSERT,param);
		// 删除
		addPermissionsByType(result,HYConst.DATE_AUTHORITY_TYPE_DELETE,HYConst.DATA_AUTHORITY_DELETE,param);
		// 查询
		addPermissionsByType(result,HYConst.DATE_AUTHORITY_TYPE_QUERY,HYConst.DATA_AUTHORITY_SELECT,param);
		// 修改
		addPermissionsByType(result,HYConst.DATE_AUTHORITY_TYPE_UPDATE,HYConst.DATA_AUTHORITY_UPDATE,param);
		return result;
	}

	private void addPermissionsByType(Map<String, Set<String>> result, String type,
			String key, Map<String, Object> param) {
		Set<String> permis = new HashSet<String>();
		param.put("type", type);
		List<Map<String, String>> l1 = sqlSession.selectList("hy.system.dataAuthority.getDataAuthorityByRoleIdAndDataType", param);
		for (Map<String, String> map : l1) {
			permis.add((String)map.get("table_name"));
		}
		if(permis.size() > 0){
			result.put(key, permis);
		}
	}
	
}
