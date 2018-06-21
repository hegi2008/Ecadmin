package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.entity.CustomAccount;

/**
 * @author peiyu
 */
public class GetCustomAccountsResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "kf_list")
    private List<CustomAccount> customAccountList;

    public List<CustomAccount> getCustomAccountList() {
        return customAccountList;
    }

    public void setCustomAccountList(List<CustomAccount> customAccountList) {
        this.customAccountList = customAccountList;
    }
}
