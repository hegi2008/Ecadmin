package com.yinhai.ec.base.service.cache.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.ec.base.service.cache.BaseCodeCacheService;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.AppCodeDomain;

@Service(value="codeCacheService")
@Transactional
public class BaseCodeCacheServiceImpl extends BaseServiceImpl implements BaseCodeCacheService{

	@Override
	@Cacheable(value=HYConst.BASE_CODE_CACHE_NAME,key="#code_string")
	public List<AppCodeDomain> getAppCodeByCodeString(String code_string) throws Exception {
		return sqlSession.selectList("hy.appcode.selectAppCodeListByCodeString", code_string);
	}

	@Override
	public String getGroupCodeString() throws Exception {
		return sqlSession.selectOne("hy.appcode.selectGroupCodeString");
	}

	@Override
	public List<AppCodeDomain> getAppCodeByDb(String code_string) {
		return sqlSession.selectList("hy.appcode.selectAppCodeListByCodeString", code_string);
	}
	
}
