package com.yinhai.shh.card.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.shh.card.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class CardServiceImpl extends BaseServiceImpl implements CardService {


	@Override
	public void updateCard(Map param){
		sqlSession.update("hy.card.manager.updateCardManager",param);
	}

	@Override
	public Map querySingleCard(Map param){
		return sqlSession.selectOne("hy.card.manager.queryByCond",param);
	}
}
 