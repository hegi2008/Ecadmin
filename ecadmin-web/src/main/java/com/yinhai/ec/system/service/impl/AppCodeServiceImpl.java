package com.yinhai.ec.system.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.ec.base.cache.ehcache.AppCodeTemplate;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.ec.system.service.AppCodeService;

@Service
@Transactional
public class AppCodeServiceImpl extends BaseServiceImpl implements AppCodeService{
	@Autowired
	private AppCodeTemplate appCodeTemplate;

	@Override
	public void saveCode(PageParam param, ResultBean bean, UserDomain loginUser) throws Exception {
		if(StringUtils.isEmpty(param.get("codeName"))){
			bean.setError(true);
			bean.setError_msg("码表名称不能为空");
			return;
		}
		if(StringUtils.isEmpty(param.get("codeString"))){
			bean.setError(true);
			bean.setError_msg("码表类别不能为空");
			return;
		}
		if(StringUtils.isEmpty(param.get("codeValue"))){
			bean.setError(true);
			bean.setError_msg("码表值不能为空");
			return;
		}
		if(StringUtils.isEmpty(param.get("codeValueName"))){
			bean.setError(true);
			bean.setError_msg("码表值描述不能为空");
			return;
		}
		if(StringUtils.isEmpty(param.get("codeType"))){
			bean.setError(true);
			bean.setError_msg("码表类型不能为空");
			return;
		}
		
		AppCodeDomain codeDomain = new AppCodeDomain();
		codeDomain.setCodeCreateTime(getSystemTimestamp());
		codeDomain.setCodeName(param.get("codeName").toString());
		codeDomain.setCodeString(param.get("codeString").toString().toUpperCase(Locale.ENGLISH));
		codeDomain.setCodeCreateer(loginUser.getUserId());
		codeDomain.setCodeValue(param.get("codeValue").toString());
		codeDomain.setCodeValueName(param.get("codeValueName").toString());
		codeDomain.setCodeType(Integer.valueOf(param.get("codeType").toString()));
		
		Integer count = sqlSession.selectOne("hy.appcode.queryCountByStringAndValue", param);
		
		if(count.intValue() > 1){
			throw new BaseAppException("修改码表出错,码表不唯一:codeString="+codeDomain.getCodeString()+";codeValue="+codeDomain.getCodeValue());
		}
		
		if(count.intValue() == 1){
			// 编辑
			Integer editCount = sqlSession.update("hy.appcode.updateSelective", codeDomain);
			if(editCount.intValue() != 1){
				throw new BaseUpdateException(editCount,"修改码表时出错");
			}
			// 如果是编辑的话 还要更新在缓存中的码表
			appCodeTemplate.updateCodeCache(codeDomain.getCodeString());
		}else{
			// 新增
			sqlSession.insert("hy.appcode.insertSelective", codeDomain);
		}
		
		bean.setSuccess_msg("码表"+codeDomain.getCodeName()+" 保存成功");
	}

	@Override
	public void deleteCode(PageParam pageParam, ResultBean bean) throws Exception {
		if(StringUtils.isEmpty(pageParam.get("codeString"))){
			bean.setError(true);
			bean.setError_msg("码表类别不能为空");
			return;
		}
		if(StringUtils.isEmpty(pageParam.get("codeValue"))){
			bean.setError(true);
			bean.setError_msg("码表值不能为空");
			return;
		}
		AppCodeDomain codeDomain = new AppCodeDomain();
		codeDomain.setCodeString(pageParam.get("codeString").toString().toUpperCase(Locale.ENGLISH));
		codeDomain.setCodeValue(pageParam.get("codeValue").toString());
		codeDomain.setCodeStatus(HYConst.STATUS_NO);
		
		if(!StringUtils.isEmpty(pageParam.get("reboot"))){
			codeDomain.setCodeStatus(HYConst.STATUS_YES);
		}else{
			appCodeTemplate.removeCodeFromCache(codeDomain.getCodeString());
		}
		
		Integer editCount = sqlSession.update("hy.appcode.updateSelective", codeDomain);
		if(editCount.intValue() != 1){
			throw new BaseUpdateException(editCount,"修改码表时出错");
		}
	}
}
