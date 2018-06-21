package com.yinhai.hyman.weixin.api.response;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class GetMaterialListResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(name="total_count")
    private int totalCount;// 该类型素材总数
    @JSONField(name="item_count")
    private int itemCount;// 本次获取的数量
    @JSONField(name="item")
    private List<Map<String, Object>> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }
}
