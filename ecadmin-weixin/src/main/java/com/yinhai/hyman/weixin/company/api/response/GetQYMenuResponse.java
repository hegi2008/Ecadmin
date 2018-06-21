package com.yinhai.hyman.weixin.company.api.response;

import com.yinhai.hyman.weixin.api.response.BaseResponse;
import com.yinhai.hyman.weixin.company.api.entity.QYMenu;

/**
 *  Response -- 获取菜单
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  @since 1.3.6
 *  ====================================================================
 */
public class GetQYMenuResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QYMenu menu;

    public QYMenu getMenu() {
        return menu;
    }

    public void setMenu(QYMenu menu) {
        this.menu = menu;
    }
}
