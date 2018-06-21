package com.yinhai.ec.base.service.impl;  

import java.sql.Timestamp;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
  
@SuppressWarnings("rawtypes")
public abstract class BaseServiceImpl implements BaseService{
	@Autowired
	protected SqlSessionTemplate sqlSession;

	@Override
	public Timestamp getSystemTimestamp() throws Exception {
		return sqlSession.selectOne("common.getSystemTimestamp");
	}
	
	@Override
	public void queryForPageWithDefault(String mapper,PageParam pageParam) {
		List list = sqlSession.selectList(mapper, pageParam);
		pageParam.setList(list);
	}
}
 