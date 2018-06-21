package com.yinhai.hyman.weixin.api.response;

import com.yinhai.hyman.weixin.api.entity.Menu;

/**
 * @author peiyu
 */
public class GetMenuResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
