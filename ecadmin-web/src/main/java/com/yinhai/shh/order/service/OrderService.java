package com.yinhai.shh.order.service;

import com.yinhai.ec.base.service.BaseService;

import java.util.Map;

public interface OrderService extends BaseService{

	void updateOrder(Map param) throws Exception;

	Map querySingleOrder(Map param) throws Exception;


}
 