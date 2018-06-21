package com.yinhai.hyman.weixin.company.api.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yinhai.hyman.weixin.api.response.BaseResponse;
import com.yinhai.hyman.weixin.company.api.entity.QYTag;

/**
 *  Response -- 获取标签列表
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  @since 1.3.6
 *  ====================================================================
 */
public class GetTagListResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name = "taglist")
    private List<QYTag> tags;

    public List<QYTag> getTags() {
        return tags;
    }

    public void setTags(List<QYTag> tags) {
        this.tags = tags;
    }
}
