package com.yinhai.shh.account.service;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.shh.account.domain.AccountDomain;

public interface AccountManagerService extends BaseService{

	void saveAccount(PageParam pageParam, ResultBean bean, UserDomain loginUser) throws Exception;

	void updateAccount(PageParam pageParam, ResultBean bean) throws Exception;

	AccountDomain querySingleAccount(PageParam pageParam) throws Exception;


}
 