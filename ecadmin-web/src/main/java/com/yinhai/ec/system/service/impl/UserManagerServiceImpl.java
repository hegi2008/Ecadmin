package com.yinhai.ec.system.service.impl;  

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.EndecryptUtils;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.system.service.UserManagerService;
  
@Service
@Transactional
public class UserManagerServiceImpl extends BaseServiceImpl implements UserManagerService{

	@Override
	public void saveUser(PageParam pageParam, ResultBean bean, UserDomain loginUser)
			throws Exception {
		
		// 验证参数
		if (pageParam.get("user_name") == null) {
			bean.setError_msg("用户名不能为空");
		}
		if (pageParam.get("user_login") == null) {
			bean.setError_msg("登陆名不能为空");
		}
		if(StringUtils.isEmpty(pageParam.get("user_id"))){
			if (pageParam.get("user_pwd") == null) {
				bean.setError_msg("密码不能为空");
			}
			if (pageParam.get("org_id") == null) {
				bean.setError_msg("请选择部门");
			}
		}
		if (pageParam.get("user_phone") == null) {
			bean.setError_msg("手机号不能为空");
		}
		if (pageParam.get("user_email") == null) {
			bean.setError_msg("邮箱不能为空");
		}
		if (pageParam.get("org_name") == null) {
			bean.setError_msg("请选择部门");
		}
		
		
		UserDomain user = new UserDomain();
		if(StringUtils.isEmpty(pageParam.get("user_id"))){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("user_login", pageParam.get("user_login"));
			checkLogin(param,bean);
			param.clear();
			param.put("user_phone", pageParam.get("user_phone"));
			checkPhone(param,bean);
			param.clear();
			param.put("user_email", pageParam.get("user_email"));
			checkEmail(param,bean);
		}else{
			UserDomain old = querySingleUser(pageParam);
			Map<String,Object> param = new HashMap<String,Object>();
			// 编辑时 只验证手机号和邮箱 登陆名不在这里修改 因为会造成不能登录的情况
			if(!old.getUserEmail().equals(pageParam.getString("user_email"))){
				param.put("user_email", pageParam.get("user_email"));
				param.put("self_id", old.getUserId());
				checkEmail(param,bean);
			}
			if(!old.getUserPhone().equals(pageParam.getString("user_phone"))){
				param.put("user_phone", pageParam.get("user_phone"));
				param.put("self_id", old.getUserId());
				checkPhone(param,bean);
			}
		}
		
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			user.setUserName(pageParam.get("user_name").toString());
			user.setUserPhone(pageParam.get("user_phone").toString());
			user.setUserEmail(pageParam.get("user_email").toString());
			user.setUserSex(pageParam.get("user_sex") == null ? null:Integer.valueOf(pageParam.get("user_sex").toString()));
			user.setUserCreater(loginUser.getUserId());
			user.setOrgId(Integer.valueOf(pageParam.getString("org_id")));
			user.setOrgName(pageParam.getString("org_name").split("/")[pageParam.getString("org_name").split("/").length-1]);
			
			if (!StringUtils.isEmpty(pageParam.get("user_id"))) {
				user.setUserId(Integer.valueOf((String)pageParam.get("user_id")));
				int count = sqlSession.update("hy.user.manager.updateUserByUserId", user);
				if (count != 1) {
					throw new BaseUpdateException(count,"更新用户时出错");
				}
				bean.setSuccess_msg("用户编辑保存完毕!");
			}else{
				user.setUserLogin(pageParam.get("user_login").toString());
				String pwd = pageParam.get("user_pwd").toString();
				String pass = EndecryptUtils.md5Password(user.getUserLogin(), pwd);
				user.setUserPwd(pass);
				sqlSession.insert("hy.user.manager.insertUser", user);
				bean.setSuccess_msg("新增用户成功!");
			}
			
		}else{
			bean.setError(true);
		}
	}

	private void checkEmail(Map<String, Object> param, ResultBean bean) {
		Integer c2 = sqlSession.selectOne("hy.user.manager.selectCountFromEmail", param);
		if (c2 > 0) {
			bean.setError_msg("邮箱已经存在,请重新输入");
			bean.setFocus("user_email");
		}
	}

	private void checkPhone(Map<String, Object> param, ResultBean bean) {
		Integer c1 = sqlSession.selectOne("hy.user.manager.selectCountFromPhone", param);
		if (c1 > 0) {
			bean.setError_msg("手机号已经存在,请重新输入");
			bean.setFocus("user_phone");
		}
	}

	private void checkLogin(Map<String, Object> param, ResultBean bean) {
		Integer c = sqlSession.selectOne("hy.user.manager.selectCountFromLoginId", param);
		if (c > 0) {
			bean.setError_msg("登陆名已经存在,请重新输入");
			bean.setFocus("user_login");
		}
	}

	@Override
	public void updateUser(PageParam pageParam, ResultBean bean)
			throws Exception {
		if (pageParam.get("user_id") == null) {
			bean.setError_msg("用户ID不能为空");
		}
		if (StringUtils.isEmpty(bean.getError_msg())){
			bean.setError(false);
			UserDomain user = new UserDomain();
			user.setUserId(Integer.valueOf((String)pageParam.get("user_id")));
			if (pageParam.get("user_lock") != null || pageParam.get("user_status") != null) {
				if (pageParam.get("user_lock") != null) {
					if(HYConst.USER_LOCK_NO.equals(Integer.valueOf(pageParam.get("user_lock").toString()))){
						user.setUserPwdWrongTimes(HYConst.STATUS_NO);
						user.setUserPwdUpdateTime(getSystemTimestamp());
					}
					user.setUserLock(Integer.valueOf((String)pageParam.get("user_lock")));
				}
				if (pageParam.get("user_status") != null) {
					user.setUserStatus(Integer.valueOf((String)pageParam.get("user_status")));
				}
				int count = sqlSession.update("hy.user.manager.updateUserByUserId", user);
				if (count != 1) {
					bean.setError(true);
					bean.setError_msg("更新用户信息时出错");
					throw new BaseUpdateException(count,"更新用户时出错");
				}
			}
		}else{
			bean.setError(true);
		}
	}

	@Override
	public UserDomain querySingleUser(PageParam pageParam) throws Exception {
		
		return sqlSession.selectOne("hy.user.manager.querySingleUser", pageParam);
	}

	@Override
	public void updateResetPassword(PageParam pageParam, ResultBean bean) throws Exception {
		if(ObjectUtils.isEmpty(pageParam.get("user_id"))){
			bean.setError(true);
			bean.setError_msg("重置密码失败,未获取到用户信息");
		}
		if(!bean.isError() && StringUtils.isEmpty(bean.getError_msg())){
			UserDomain user = querySingleUser(pageParam);
			String password = EndecryptUtils.md5Password(user.getUserLogin(), user.getUserLogin());
			UserDomain userDomain = new UserDomain();
			userDomain.setUserId(user.getUserId());
			userDomain.setUserPwd(password);
			userDomain.setUserPwdWrongTimes(0);
			userDomain.setUserPwdUpdateTime(getSystemTimestamp());
			int count = sqlSession.update("hy.user.manager.updateUserByUserId", userDomain);
			if (count != 1) {
				throw new BaseUpdateException(count, "重置用户【"+user.getUserName()+"】密码失败");
			}
			bean.setSuccess_msg("用户【"+user.getUserName()+"】密码已重置");
		}
	}
	
}
 