package com.yinhai.shh.registration.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.registration.service.RegService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class RegServiceImpl extends BaseServiceImpl implements RegService {


	@Override
	public void updateReg(Map param){
		sqlSession.update("hy.reg.manager.updateRegistrationDetailByOrderId",param);
	}

	@Override
	public Map querySingleReg(Map param){
		return sqlSession.selectOne("hy.reg.manager.queryByCond",param);
	}
}
 