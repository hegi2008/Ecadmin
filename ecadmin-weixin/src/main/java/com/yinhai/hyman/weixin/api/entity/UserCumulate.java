package com.yinhai.hyman.weixin.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 累计用户数据
 *
 * @author peiyu
 */
public class UserCumulate extends BaseDataCube {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "cumulate_user")
    private Integer cumulateUser;

    public Integer getCumulateUser() {
        return cumulateUser;
    }

    public void setCumulateUser(Integer cumulateUser) {
        this.cumulateUser = cumulateUser;
    }
}
