package com.yinhai.hyman.weixin.company.message;

import com.yinhai.hyman.weixin.api.entity.Article;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class QYMpArticle extends Article {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QYMpArticle(String thumbMediaId, String author, String title, String contentSourceUrl, String content, String digest, Integer showConverPic) {
        super(thumbMediaId, author, title, contentSourceUrl, content, digest, showConverPic);
    }

}
