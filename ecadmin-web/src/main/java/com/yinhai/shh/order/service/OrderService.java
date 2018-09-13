package com.yinhai.shh.order.service;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.shh.account.domain.AccountDomain;
import com.yinhai.shh.order.domain.OrderEntity;

public interface OrderService extends BaseService{

	void saveOrder(OrderEntity orderEntity) throws Exception;

	void updateOrder(OrderEntity orderEntity) throws Exception;

	OrderEntity querySingleOrder(OrderEntity orderEntity) throws Exception;


}
 