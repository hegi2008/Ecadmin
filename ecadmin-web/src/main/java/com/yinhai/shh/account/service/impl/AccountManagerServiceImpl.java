package com.yinhai.shh.account.service.impl;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.shh.account.domain.AccountDomain;
import com.yinhai.shh.account.service.AccountManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@Transactional
public class AccountManagerServiceImpl extends BaseServiceImpl implements AccountManagerService{

	@Override
	public void saveAccount(PageParam pageParam, ResultBean bean, UserDomain loginUser)
			throws Exception {
		
		// 验证参数
		if (pageParam.get("out_platform_id") == null) {
			bean.setError_msg("账户ID不能为空");
		}
		if (pageParam.get("channel") == null) {
			bean.setError_msg("渠道不能为空");
		}
		

		AccountDomain accountDomain = new AccountDomain();
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			accountDomain.setDes(pageParam.get("des")+"");
			accountDomain.setOut_platform_id(pageParam.get("out_platform_id").toString());
			accountDomain.setChannel(pageParam.get("channel").toString());

			if (!StringUtils.isEmpty(pageParam.get("edit"))) {
				int count = sqlSession.update("hy.account.manager.updateAccountByAccountId", accountDomain);
				if (count != 1) {
					throw new BaseUpdateException(count,"更新用户时出错");
				}
				bean.setSuccess_msg("用户编辑保存完毕!");
			}else{
				sqlSession.insert("hy.account.manager.insertAccount", accountDomain);
				bean.setSuccess_msg("新增用户成功!");
			}
			
		}else{
			bean.setError(true);
		}
	}


	@Override
	public void updateAccount(PageParam pageParam, ResultBean bean)
			throws Exception {
	}

	@Override
	public AccountDomain querySingleAccount(PageParam pageParam) throws Exception {
		
		return sqlSession.selectOne("hy.account.manager.querySingleAccount", pageParam);
	}


	
}
 