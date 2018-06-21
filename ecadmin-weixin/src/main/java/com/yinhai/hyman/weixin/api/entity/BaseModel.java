package com.yinhai.hyman.weixin.api.entity;

import com.yinhai.hyman.weixin.util.JSONUtil;

/**
 * 抽象实体类
 *
 * @author peiyu
 */
public abstract class BaseModel implements Model {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
