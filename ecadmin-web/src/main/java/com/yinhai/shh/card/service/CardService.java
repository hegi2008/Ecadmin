package com.yinhai.shh.card.service;

import com.yinhai.ec.base.service.BaseService;

import java.util.Map;

public interface CardService extends BaseService{

	void updateCard(Map param) throws Exception;

	Map querySingleCard(Map param) throws Exception;


}
 