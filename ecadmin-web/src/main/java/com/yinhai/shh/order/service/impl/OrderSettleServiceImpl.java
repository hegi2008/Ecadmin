package com.yinhai.shh.order.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.order.domain.OrderSettlementEntity;
import com.yinhai.shh.order.service.OrderSettleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OrderSettleServiceImpl extends BaseServiceImpl implements OrderSettleService {


	@Override
	public List<OrderSettlementEntity> querySettleRecords(OrderSettlementEntity orderSettlementEntity) {
		return sqlSession.selectList("hy.ordersettment.manager.queryByCond",orderSettlementEntity);
	}
}
 