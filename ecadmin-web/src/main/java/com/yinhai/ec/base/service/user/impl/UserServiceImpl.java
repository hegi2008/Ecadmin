package com.yinhai.ec.base.service.user.impl;  

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.service.user.UserService;
import com.yinhai.ec.base.util.EndecryptUtils;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
 
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public UserDomain getUserByLogin(String username, String password) throws AuthenticationException {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			throw new AuthenticationException("用户名/密码不能为空");
		}
		UserDomain user = findUserByUsername(username);
		if (HYConst.STATUS_NO.equals(user.getUserStatus())) {
			throw new AuthenticationException("用户已失效,不能登录");
		}
		if(HYConst.USER_LOCK_YES.equals(user.getUserLock())){
			throw new AuthenticationException("用户已锁定,不能登录");
		}
		logger.debug("用户有效性验证结束,返回Realm认证");
		return user;
	}

	/**
	  * @package com.yinhai.ec.base.service.user.impl
	  * @method UpdateUserPwdWrongTime 方法 
	  * @describe <p>方法说明:更新密码输入错误次数</p>
	  * @return void 
	  * @author cjh
	 * @date 2016年3月28日 下午4:50:28
	 */
	@Override
	public void UpdateUserPwdWrongTime(String username, Integer statusNo) throws Exception {
		if(StringUtils.isEmpty(username)){
			return;
		}
		UserDomain user = findUserByUsername(username);
		UserDomain userDomain = new UserDomain();
		userDomain.setUserPwdUpdateTime((Timestamp)sqlSession.selectOne("common.getSystemTimestamp"));
		if(HYConst.STATUS_NO.equals(statusNo)){
			userDomain.setUserPwdWrongTimes(statusNo);
			userDomain.setUserId(user.getUserId());
			userDomain.setUserLock(HYConst.USER_LOCK_NO);
			int count = sqlSession.update("hy.user.updateUserByUserId", userDomain);
			if(count != 1){
				throw new RuntimeException("login failed because UpdateUserPwdWrongTime with wrong count");
			}
		}else{
			if(2 == user.getUserPwdWrongTimes().intValue()){
				userDomain.setUserPwdWrongTimes(user.getUserPwdWrongTimes().intValue()+1);
				userDomain.setUserId(user.getUserId());
				userDomain.setUserLock(HYConst.USER_LOCK_YES);
				int count = sqlSession.update("hy.user.updateUserByUserId", userDomain);
				if(count != 1){
					throw new RuntimeException("login failed because UpdateUserPwdWrongTime with wrong count");
				}
			}else if(user.getUserPwdWrongTimes().intValue() < 2){
				userDomain.setUserPwdWrongTimes(user.getUserPwdWrongTimes().intValue()+1);
				userDomain.setUserId(user.getUserId());
				int count = sqlSession.update("hy.user.updateUserByUserId", userDomain);
				if(count != 1){
					throw new RuntimeException("login failed because UpdateUserPwdWrongTime with wrong count");
				}
			}
		}
	}
	
	@Override
	public UserDomain findUserByUsername(String username) throws UnknownAccountException{
		Map<String, String> param = new HashMap<String, String>();
		param.put("user_login", username);
		try {
			List<UserDomain> list = sqlSession.selectList("hy.user.findUserByUsername",param);
			if (list.size() == 0) {
				throw new AuthenticationException("用户: "+username +" 不存在");
			}
			if (list.size() > 1) {
				throw new AuthenticationException("获取用户: "+username+" 出现错误,请联系系统管理员");
			}
			return list.get(0);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("find user wrong {}", e.getMessage());
			}
			throw new UnknownAccountException("查询用户失败");
		}
	}

	@Override
	public List<UserRolesDomain> getUserRolesByUserId(Integer userId)
			throws Exception {
		return sqlSession.selectList("hy.user.getUserRolesByUserId", userId);
	}

	@Override
	public List<Map<String, Object>> getUserPermissionUrlsByRoleString(
			String roleString) throws Exception {
		
		return sqlSession.selectList("hy.user.getUserPermissionUrlsByRoleString", roleString);
	}

	@Override
	public void updateUserInfo(UserDomain user, ResultBean bean) throws Exception {
		if(user.getUserId() != null){
			int count = sqlSession.update("hy.user.manager.updateUserByUserId", user);
			if (count != 1) {
				throw new BaseUpdateException(count, "修改用户信息失败");
			}
		}
	}

	@Override
	public void updateUserPassword(PageParam pageParam, ResultBean bean) throws Exception {
		UserDomain user = new UserDomain();
		String userPwd_old = null;
		String userPwd = null;
		if(StringUtils.isEmpty(pageParam.get("userLogin"))){
			bean.setError(true);
			bean.setError_msg("登陆账号不能为空");
		}else{
			user.setUserLogin(pageParam.getString("userLogin"));
		}
		
		if(StringUtils.isEmpty(pageParam.get("userPwd_old"))){
			bean.setError(true);
			bean.setError_msg("原密码不能为空");
		}else{
			userPwd_old = pageParam.getString("userPwd_old");
		}
		
		if(StringUtils.isEmpty(pageParam.get("userPwd"))){
			bean.setError(true);
			bean.setError_msg("新密码不能为空");
		}else{
			userPwd = pageParam.getString("userPwd");
		}
		
		if(!bean.isError() && StringUtils.isEmpty(bean.getError_msg())){
			Integer count = sqlSession.selectOne("hy.user.selectCountFromLoginId", user.getUserLogin());
			if(count > 0){
				userPwd_old = EndecryptUtils.md5Password(user.getUserLogin(),userPwd_old);
				Map<String, String> param = new HashMap<String, String>();
				param.put("user_login", user.getUserLogin());
				param.put("user_pwd", userPwd_old);
				List<UserDomain> list = sqlSession.selectList("hy.user.selectByLoginAndPassword",param);
				if(list == null || list.size() == 0){
					bean.setError(true);
					bean.setError_msg("原密码输入错误");
				}else{
					if(list.size() > 1){
						bean.setError(true);
						bean.setError_msg("修改密码出错,获取到多条用户信息,请核实");
					}else{
						user = list.get(0);
						userPwd = EndecryptUtils.md5Password(user.getUserLogin(),userPwd);
						if(userPwd_old.equals(userPwd)){
							bean.setError(true);
							bean.setError_msg("新密码不能与原密码相同");
						}else{
							UserDomain domain = new UserDomain();
							domain.setUserId(user.getUserId());
							domain.setUserPwd(userPwd);
							domain.setUserPwdUpdateTime(getSystemTimestamp());
							updateUserInfo(domain,bean);
							bean.setSuccess_msg("用户:"+user.getUserName()+" 修改密码成功");
						}
					}
				}
			}else{
				bean.setError(true);
				bean.setError_msg("登陆账户输入错误");
			}
		}
	}
}
 