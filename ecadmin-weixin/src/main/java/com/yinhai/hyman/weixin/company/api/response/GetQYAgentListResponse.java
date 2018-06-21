package com.yinhai.hyman.weixin.company.api.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;
import com.yinhai.hyman.weixin.company.api.entity.QYAgent;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class GetQYAgentListResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "agentlist")
    public List<QYAgent> agentList;

    public List<QYAgent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<QYAgent> agentList) {
        this.agentList = agentList;
    }
}
