package com.yinhai.common;

public interface IConst {

	/**
	 * 公共常量
	 */
	
	/** 默认店铺ID */
	public static final int DEFAULT_STORE_ID = 0;
	/** 是否展示：展示  */
	public static final int IS_SHOW_YES = 1;
	/** 是否展示：不展示  */
	public static final int IS_SHOW_NO = 0;
	/** 是否删除：已删除  */
	public static final int IS_DELETED_YES = 1;
	/** 是否删除：未删除 */
	public static final int IS_DELETED_NO = 0;
	/** 默认平台管理员（developer）YAB003键值 */
	public static final String DEFAULT_YAB003 = "9999";
	
	/**广告状态**/
	/**0 注销 1启用 2删除**/
	Integer AD_STATUS_0=0;
	/**0 注销 1启用 2删除**/
	Integer AD_STATUS_1=1;
	/**0 注销 1启用 2删除**/
	Integer AD_STATUS_2=2;
	
	/**广告相册**/
	String ALBUM_SYS_ADV = "ALBUM_SYS_ADV";
	
	/** 广告审核状态 未审核*/
	Integer ADV_CHECKED_0=0;
	
	/** 广告审核状态 审核通过*/
	Integer ADV_CHECKED_1=1;
	
	/** 广告审核状态 审核未通过*/
	Integer ADV_CHECKED_2=2;
	
	/** 文章状态 0无效*/
	Integer ARTICLE_STATUS_0 = 0;
	/** 文章状态 1有效*/
	Integer ARTICLE_STATUS_1 = 1;
	/** 文章状态 2通过审核*/
	Integer ARTICLE_STATUS_2 = 2;
	/** 文章状态 3逻辑删除*/
	Integer ARTICLE_STATUS_3 = 3;
	/** 文章是否置顶 0不置顶*/
	Integer ARTICLE_TOP_0 = 0;
	/** 文章是否置顶 0置顶*/
	Integer ARTICLE_TOP_1 = 1;
	/** 文章顶级分类ID 0*/
	Integer ARTICLE_CATE_TOPID = 0;
	
}
