package com.yinhai.shh.order.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.order.domain.OrderEntity;
import com.yinhai.shh.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {


	@Override
	public void saveOrder(OrderEntity orderEntity) throws Exception {

	}

	@Override
	public void updateOrder(OrderEntity orderEntity) throws Exception {

	}

	@Override
	public OrderEntity querySingleOrder(OrderEntity orderEntity) throws Exception {
		return null;
	}
}
 