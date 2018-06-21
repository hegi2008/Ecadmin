package com.yinhai.hyman.weixin.api.response;

import java.util.List;

import com.yinhai.hyman.weixin.api.entity.ArticleTotal;

/**
 * @author peiyu
 */
public class GetArticleTotalResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ArticleTotal> list;

    public List<ArticleTotal> getList() {
        return list;
    }

    public void setList(List<ArticleTotal> list) {
        this.list = list;
    }
}
