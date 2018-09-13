package com.yinhai.shh.order.service;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.shh.order.domain.OrderSettlementEntity;

import java.util.List;

public interface OrderSettleService extends BaseService{


	List<OrderSettlementEntity> querySettleRecords(OrderSettlementEntity orderSettlementEntity) ;


}
 