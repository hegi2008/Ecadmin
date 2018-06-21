package com.yinhai.ec.system.service;  

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
  
public interface UserManagerService extends BaseService{

	void saveUser(PageParam pageParam, ResultBean bean, UserDomain loginUser) throws Exception;

	void updateUser(PageParam pageParam, ResultBean bean) throws Exception;

	UserDomain querySingleUser(PageParam pageParam) throws Exception;

	void updateResetPassword(PageParam pageParam, ResultBean bean) throws Exception;
	
}
 