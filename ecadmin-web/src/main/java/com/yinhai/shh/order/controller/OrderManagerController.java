package com.yinhai.shh.order.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping("/updateOrder")
	@ResponseBody
	public Object updateOrder(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		orderService.updateOrder(pageParam.getMap());
		bean.setError_msg("修改成功");
		return bean;
	}
	@RequestMapping("/updateRefund")
	@ResponseBody
	public Object updateRefund(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		//todo 调用退费接口
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		Map param = pageParam.getMap();
		param.put("hisResult","04");//设置his业务退费
		param.put("payResult","04");//设置支付状态退费
		orderService.updateOrder(param);
		bean.setError_msg("退费成功");
		return bean;
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
	@RequestMapping("/toDetail")
	public String toDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> hisresults = getAppCodeByCodeString("HISRESULT");
		List<AppCodeDomain> pay_types = getAppCodeByCodeString("PAY_TYPE");
		List<AppCodeDomain> payways = getAppCodeByCodeString("PAYWAY");
		List<AppCodeDomain> paychannels = getAppCodeByCodeString("PAYCHANNEL");
		List<AppCodeDomain> pay_results = getAppCodeByCodeString("PAY_RESULT");
		List<AppCodeDomain> ywlxs = getAppCodeByCodeString("YWLX");
		modelMap.put("hisresults",hisresults);
		modelMap.put("pay_types",pay_types);
		modelMap.put("payways",payways);
		modelMap.put("paychannels",paychannels);
		modelMap.put("pay_results",pay_results);
		modelMap.put("ywlxs",ywlxs);
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			Map param = new HashMap();
			param.put("orderId",pageParam.getString("orderId"));
			Map order = orderService.querySingleOrder(param);
			modelMap.put("order",order);
		}
		return "/shh/order/orderDetail.jsp";
	}
	@RequestMapping("/toEditDetail")
	public String toEditDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> hisresults = getAppCodeByCodeString("HISRESULT");
		List<AppCodeDomain> pay_types = getAppCodeByCodeString("PAY_TYPE");
		List<AppCodeDomain> payways = getAppCodeByCodeString("PAYWAY");
		List<AppCodeDomain> paychannels = getAppCodeByCodeString("PAYCHANNEL");
		List<AppCodeDomain> pay_results = getAppCodeByCodeString("PAY_RESULT");
		List<AppCodeDomain> ywlxs = getAppCodeByCodeString("YWLX");
		modelMap.put("hisresults",hisresults);
		modelMap.put("pay_types",pay_types);
		modelMap.put("payways",payways);
		modelMap.put("paychannels",paychannels);
		modelMap.put("pay_results",pay_results);
		modelMap.put("ywlxs",ywlxs);
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			Map param = new HashMap();
			param.put("orderId",pageParam.getString("orderId"));
			Map order = orderService.querySingleOrder(param);
			modelMap.put("order",order);
		}
		return "/shh/order/orderEdit.jsp";
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
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public Object getOrderDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		ResultBean bean = new ResultBean();
		bean.setError(true);
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			Map param = new HashMap();
			param.put("orderId",pageParam.getString("orderId"));
			Map order = orderService.querySingleOrder(param);
			if(order != null && order.size()>0){
				bean.setError(false);
			}
		}
		return bean;
	}
 }
 