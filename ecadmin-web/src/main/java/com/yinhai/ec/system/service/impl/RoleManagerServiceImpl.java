package com.yinhai.ec.system.service.impl;  

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
import com.yinhai.ec.system.service.RoleManagerService;
  
@Service
@Transactional
public class RoleManagerServiceImpl extends BaseServiceImpl implements RoleManagerService{
	/**
	 * 查询角色list
	 */
	@Override
	public void queryUserRoleList(PageParam pageParam) throws Exception {
		List<Map<String, Object>> list = sqlSession.selectList("hy.role.manager.queryListRoleManager",pageParam);
		pageParam.setTotal(Long.valueOf(list.size()));
		pageParam.setList(list);
	}
	/**
	 * 保存角色信息
	 */
	@Override
	public void saveRole(PageParam pageParam, ResultBean bean, UserDomain loginUser)
			throws Exception {
		// 验证参数
		if (StringUtils.isEmpty(pageParam.get("role_name"))) {
			bean.setError_msg("角色名不能为空");
		}
		if (StringUtils.isEmpty(pageParam.get("role_string"))) {
			bean.setError_msg("角色验证字符串不能为空");
		}
		if (StringUtils.isEmpty(pageParam.get("role_type"))) {
			bean.setError_msg("角色类型不能为空");
		}
		
		if (StringUtils.isEmpty(pageParam.get("org_id"))) {
			bean.setError_msg("请选择部门");
		}
		if (StringUtils.isEmpty(pageParam.get("org_name"))) {
			bean.setError_msg("请选择部门");
		}
		
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			UserRolesDomain roles = new UserRolesDomain();
			roles.setRoleName(pageParam.get("role_name").toString());
			roles.setRoleString(pageParam.get("role_string").toString());
			Object r_type =  pageParam.get("role_type");
			roles.setRoleType(Integer.valueOf((String)r_type));
			roles.setRoleStatus(HYConst.STATUS_YES);//默认状态为有效
			roles.setRoleCreater(loginUser.getUserId());
			
			// 检查该角色是否有效
			checkRoleEnable(pageParam,bean);
			if(!StringUtils.isEmpty(bean.getError_msg()) && bean.isError()){
				return;
			}
			
			if (!StringUtils.isEmpty(pageParam.get("role_id"))) {//修改
				roles.setRoleId(Integer.valueOf((String)pageParam.get("role_id")));
				int count = sqlSession.update("hy.role.manager.updateRoleByRoleId", roles);
				if (count != 1) {
					throw new BaseUpdateException(count,"更新角色时出错");
				}
				bean.setSuccess_msg("角色编辑保存完毕!");
			}else{//新增
				sqlSession.insert("hy.role.manager.insertRole", roles);
				pageParam.put("role_id", roles.getRoleId());
				bean.setSuccess_msg("新增角色成功!");
			}
			
			sqlSession.delete("hy.role.manager.deleteOrgRolesByRoleId", pageParam);
			sqlSession.insert("hy.role.manager.insertOrgRoles", pageParam);
			
		}else{
			bean.setError(true);
		}
	}
	
	/**
	  * @package com.yinhai.ec.system.service.impl
	  * @method checkRoleEnable 方法 
	  * @describe <p>方法说明:检查角色是否有效</p>
	  * @return ResultBean 
	  * @author cjh
	  * @date 2016年2月22日 上午9:37:14
	 */
	private void checkRoleEnable(PageParam pageParam, ResultBean bean) {
		Integer c = sqlSession.selectOne("hy.role.manager.selectCountFromRoleName", pageParam);
		if (c > 0) {
			bean.setError_msg("角色名【"+pageParam.get("role_name")+"】已经存在,请重新输入");
			bean.setError(true);
			return;
		}
		Integer d = sqlSession.selectOne("hy.role.manager.selectCountFromRoleString", pageParam);
		if (d > 0) {
			bean.setError_msg("角色验证字符串【"+pageParam.get("role_string")+"】已经存在,请重新输入");
			bean.setError(true);
			return;
		}
	}
	
	/**
	 * 更新角色
	 */
	@Override
	public void updateRole(PageParam pageParam, ResultBean bean) throws Exception {
		if (pageParam.get("role_id") == null) {
			bean.setError_msg("角色ID不能为空");
		}
		if (StringUtils.isEmpty(bean.getError_msg())){
			bean.setError(false);
			UserRolesDomain roles = new UserRolesDomain();
			roles.setRoleId(Integer.valueOf((String)pageParam.get("role_id")));
			if (pageParam.get("user_status") != null) {
				roles.setRoleStatus(Integer.valueOf((String)pageParam.get("role_status")));
				int count = sqlSession.update("hy.role.manager.updateRoleByRoleId", roles);
				if (count != 1) {
					bean.setError(true);
					bean.setError_msg("更新角色信息时出错");
					throw new BaseUpdateException(count,"更新角色时出错");
				}
			}
		
		}else{
			bean.setError(true);
		}
	}
	
	/**
	 * 删除角色
	 */
	@Override
	public void deleteRole(Map<?, ?> param, ResultBean bean) throws Exception {
		if (param.get("role_id") == null || "".equals(param.get("role_id").toString())) {
			bean.setError(true);
			bean.setError_msg("删除失败,未获取到需要删除的角色信息");
		}
		
		if("1".equals(param.get("role_id").toString())){
			bean.setError(true);
			bean.setError_msg("不能删除系统管理员");
		}
		
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			UserRolesDomain roles = new UserRolesDomain();
			roles.setRoleId(Integer.valueOf(param.get("role_id").toString()));
			roles.setRoleStatus(HYConst.STATUS_NO);//将角色状态设置为无效
		
			Integer c1 = sqlSession.selectOne("hy.role.manager.selectCountFromRelation", roles.getRoleId());
			Integer c2 = sqlSession.selectOne("hy.role.manager.selectCountFromAuth", roles.getRoleId());
			if(c1 > 0 || c2 > 0){
				if (c1 > 0) {
					// 删除角色权限不需要数量验证
					sqlSession.delete("hy.role.manager.deleteRelationByRoleId",roles);
				}
				if (c2 > 0) {
					// 删除角色权限不需要数量验证
					sqlSession.delete("hy.role.manager.deleteAuthByRoleId",roles);
				}
			}
			int count = sqlSession.update("hy.role.manager.updateRoleByRoleId", roles);
			if (count != 1) {
				throw new BaseUpdateException(count,"更新角色时出错");
			}
			
			// 删除角色与部门的关联关系
			PageParam pageParam = new PageParam();
			pageParam.put("role_id", roles.getRoleId());
			sqlSession.delete("hy.role.manager.deleteOrgRolesByRoleId", pageParam);
			
			bean.setSuccess_msg("删除角色成功");
		}
	}
	@Override
	public UserRolesDomain querySingleRole(Integer role_id) throws Exception {
		return sqlSession.selectOne("hy.role.manager.querySingleRole", role_id);
	}
	@Override
	public void updateRoleUser(PageParam pageParam, ResultBean bean) throws Exception {
		if(ObjectUtils.isEmpty(pageParam.get("role_id"))){
			bean.setError(true);
			bean.setError_msg("操作失败,未获取到角色信息");
		}
		if(ObjectUtils.isEmpty(pageParam.get("user_id"))){
			bean.setError(true);
			bean.setError_msg("操作失败,未获取到人员信息");
		}
		if(ObjectUtils.isEmpty(pageParam.get("status"))){
			bean.setError(true);
			bean.setError_msg("操作失败");
		}
		if (!bean.isError() && StringUtils.isEmpty(bean.getError_msg())) {
			if (HYConst.STATUS_YES.equals(Integer.valueOf(pageParam.getString("status")))) {
				// 增加 
				sqlSession.insert("hy.role.manager.insertRoleUser", pageParam);
			}else if(HYConst.STATUS_NO.equals(Integer.valueOf(pageParam.getString("status")))){
				// 删除
				sqlSession.delete("hy.role.manager.deleteRoleUser", pageParam);
			}
		}
	}
	@Override
	public Map<String, Object> queryFullRole(Integer role_id) {
		return sqlSession.selectOne("hy.role.manager.queryFullRole", role_id);
	}
	
}
 