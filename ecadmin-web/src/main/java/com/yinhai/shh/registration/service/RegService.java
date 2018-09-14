package com.yinhai.shh.registration.service;

import com.yinhai.ec.base.service.BaseService;

import java.util.Map;

public interface RegService extends BaseService{

	void updateReg(Map param) throws Exception;

	Map querySingleReg(Map param) throws Exception;


}
 