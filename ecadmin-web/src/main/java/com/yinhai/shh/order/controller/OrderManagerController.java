package com.yinhai.shh.order.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.order.domain.OrderSettlementEntity;
import com.yinhai.shh.order.service.OrderService;
import com.yinhai.shh.order.service.OrderSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("shh/order/orderManager")
public class OrderManagerController extends BaseController{
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderSettleService orderSettleService;
	
	@RequestMapping("/default")
	public String execute(ModelMap map){
		return "/shh/order/orderList.jsp";
	}
	
	@RequestMapping("/queryOrderList")
	@ResponseBody
	public Object queryOrderList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("add_time_value"))){
			String create_time = pageParam.getString("add_time_value");
			pageParam.put("startDate", create_time.split("~")[0].trim()+" 00:00:00");
			pageParam.put("endDate", create_time.split("~")[1].trim()+" 23:59:59");
		}
		orderService.queryForPageWithDefault("hy.order.manager.queryListPageOrderManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/createOrder")
	public String createOrder(ModelMap modelMap, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/saveOrder")
	@ResponseBody
	public Object saveOrder(HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/updateOrder")
	@ResponseBody
	public Object updateOrder(HttpServletRequest request){
		return null;
	}
	@RequestMapping("/toSettle")
	public String toSettle(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			modelMap.put("orderId",pageParam.getString("orderId"));
		}
		return "/shh/order/settle.jsp";
	}
	@RequestMapping("/getSettle")
	@ResponseBody
	public Object getSettle(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		//查询订单结算详情
		List<OrderSettlementEntity> orderSettlementEntityList = new ArrayList<>();
		OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			orderSettlementEntity.setOrderId(pageParam.getString("orderId"));
			orderSettlementEntityList = orderSettleService.querySettleRecords(orderSettlementEntity);
		}
		return orderSettlementEntityList;
	}
 }
 