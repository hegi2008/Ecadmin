package com.yinhai.shh.registration.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.registration.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("shh/reg/regManager")
public class RegManagerController extends BaseController{
	@Autowired
	private RegService regService;

	
	@RequestMapping("/default")
	public String execute(ModelMap map){
		return "/shh/reg/regList.jsp";
	}
	
	@RequestMapping("/queryRegList")
	@ResponseBody
	public Object queryRegList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("add_time_value"))){
			String create_time = pageParam.getString("add_time_value");
			pageParam.put("startDate", create_time.split("~")[0].trim()+" 00:00:00");
			pageParam.put("endDate", create_time.split("~")[1].trim()+" 23:59:59");
		}
		if(!StringUtils.isEmpty(pageParam.get("add_time_value_ts"))){
			String create_time = pageParam.getString("add_time_value_ts");
			pageParam.put("tsstartDate", create_time.split("~")[0].trim()+" 00:00:00");
			pageParam.put("tsendDate", create_time.split("~")[1].trim()+" 23:59:59");
		}
		regService.queryForPageWithDefault("hy.reg.manager.queryListPageRegManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/updateReg")
	@ResponseBody
	public Object updateReg(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		//todo 调用his取消锁号
		Map param = pageParam.getMap();
		param.put("status","2");//取消登记
		regService.updateReg(pageParam.getMap());
		bean.setError_msg("修改成功");
		return bean;
	}

	@RequestMapping("/toDetail")
	public String toDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> regstatus = getAppCodeByCodeString("REG_STATUS");
		modelMap.put("regstatus",regstatus);
		if(pageParam.getString("orderId") != null && !"".equals(pageParam.getString("orderId").trim())){
			Map param = new HashMap();
			param.put("orderId",pageParam.getString("orderId"));
			Map reg = regService.querySingleReg(param);
			modelMap.put("reg",reg);
		}
		return "/shh/reg/regDetail.jsp";
	}
 }
 