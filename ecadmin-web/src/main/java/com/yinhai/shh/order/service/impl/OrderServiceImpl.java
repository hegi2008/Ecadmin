package com.yinhai.shh.order.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {


	@Override
	public void updateOrder(Map param){
		sqlSession.update("hy.order.manager.updateOrderByOrderId",param);
	}

	@Override
	public Map querySingleOrder(Map param){
		return sqlSession.selectOne("hy.order.manager.queryByCond",param);
	}
}
 