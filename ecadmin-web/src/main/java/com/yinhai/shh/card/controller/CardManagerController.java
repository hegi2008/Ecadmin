package com.yinhai.shh.card.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.util.StringUtils;
import com.yinhai.shh.card.service.CardService;
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
@RequestMapping("shh/card/cardManager")
public class CardManagerController extends BaseController{
	@Autowired
	private CardService cardService;
	@RequestMapping("/default")
	public String execute(ModelMap map){
		return "/shh/card/cardList.jsp";
	}
	
	@RequestMapping("/queryCardList")
	@ResponseBody
	public Object queryOrderList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(!StringUtils.isEmpty(pageParam.get("add_time_value"))){
			String create_time = pageParam.getString("add_time_value");
			pageParam.put("startDate", create_time.split("~")[0].trim()+" 00:00:00");
			pageParam.put("endDate", create_time.split("~")[1].trim()+" 23:59:59");
		}
		cardService.queryForPageWithDefault("hy.card.manager.queryListPageCardManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	@RequestMapping("/updateCard")
	@ResponseBody
	public Object updateCard(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		cardService.updateCard(pageParam.getMap());
		bean.setError_msg("修改成功");
		return bean;
	}
	@RequestMapping("/toDetail")
	public String toDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> paychannels = getAppCodeByCodeString("PAYCHANNEL");
		List<AppCodeDomain> ismrjzks = getAppCodeByCodeString("ISMRJZK");
		List<AppCodeDomain> cardstatus = getAppCodeByCodeString("CARDSTATUS");
		List<AppCodeDomain> cardtypes = getAppCodeByCodeString("CARDTYPE");
		modelMap.put("cardstatus",cardstatus);
		modelMap.put("ismrjzks",ismrjzks);
		modelMap.put("paychannels",paychannels);
		modelMap.put("cardtypes",cardtypes);
		if(pageParam.getString("outPlatformId") != null && !"".equals(pageParam.getString("outPlatformId").trim())
				&& pageParam.getString("cardno") != null && !"".equals(pageParam.getString("cardno").trim())
				&& pageParam.getString("channel") != null && !"".equals(pageParam.getString("channel").trim())
				&& pageParam.getString("agencyNum") != null && !"".equals(pageParam.getString("agencyNum").trim())
				&& pageParam.getString("status") != null && !"".equals(pageParam.getString("status").trim())){
			Map param = new HashMap();
			param.put("outPlatformId",pageParam.getString("outPlatformId"));
			param.put("cardno",pageParam.getString("cardno"));
			param.put("status",pageParam.getString("status"));
			param.put("channel",pageParam.getString("channel"));
			param.put("agencyNum",pageParam.getString("agencyNum"));
			Map card = cardService.querySingleCard(param);
			modelMap.put("card",card);
		}
		return "/shh/card/cardDetail.jsp";
	}
	@RequestMapping("/toEditDetail")
	public String toEditDetail(ModelMap modelMap, HttpServletRequest request) throws Exception {
		//创建参数对象
		PageParam pageParam = getPageParam(request);
		List<AppCodeDomain> paychannels = getAppCodeByCodeString("PAYCHANNEL");
		List<AppCodeDomain> ismrjzks = getAppCodeByCodeString("ISMRJZK");
		List<AppCodeDomain> cardstatus = getAppCodeByCodeString("CARDSTATUS");
		List<AppCodeDomain> cardtypes = getAppCodeByCodeString("CARDTYPE");
		modelMap.put("cardstatus",cardstatus);
		modelMap.put("ismrjzks",ismrjzks);
		modelMap.put("paychannels",paychannels);
		modelMap.put("cardtypes",cardtypes);
		if(pageParam.getString("outPlatformId") != null && !"".equals(pageParam.getString("outPlatformId").trim())
				&& pageParam.getString("cardno") != null && !"".equals(pageParam.getString("cardno").trim())
				&& pageParam.getString("channel") != null && !"".equals(pageParam.getString("channel").trim())
				&& pageParam.getString("agencyNum") != null && !"".equals(pageParam.getString("agencyNum").trim())
				&& pageParam.getString("status") != null && !"".equals(pageParam.getString("status").trim())){
			Map param = new HashMap();
			param.put("outPlatformId",pageParam.getString("outPlatformId"));
			param.put("cardno",pageParam.getString("cardno"));
			param.put("status",pageParam.getString("status"));
			param.put("channel",pageParam.getString("channel"));
			param.put("agencyNum",pageParam.getString("agencyNum"));
			Map card = cardService.querySingleCard(param);
			modelMap.put("card",card);
		}
		return "/shh/card/cardEdit.jsp";
	}

 }
 