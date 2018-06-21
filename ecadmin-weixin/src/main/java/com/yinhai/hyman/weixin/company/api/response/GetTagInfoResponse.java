package com.yinhai.hyman.weixin.company.api.response;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;

/**
 *  Response -- 标签信息
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  @since 1.3.6
 *  ====================================================================
 */
public class GetTagInfoResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "userlist")
    private List<Map<String, String>> users;
    @JSONField(name = "partylist")
    private List<Integer> partys;

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, String>> users) {
        this.users = users;
    }

    public List<Integer> getPartys() {
        return partys;
    }

    public void setPartys(List<Integer> partys) {
        this.partys = partys;
    }
}
