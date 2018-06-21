package com.yinhai.ec.base.util;  

/**
* @package com.yinhai.ec.base.util
* <p>Title: HYConst.java</p>
* <p>Description: HY常量</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2015-12-31 上午9:58:43
* @version 1.0
 */
public class HYConst {
	/*** status 1有效*/
	public static final Integer STATUS_YES = 1;
	/*** status 0无效*/
	public static final Integer STATUS_NO = 0;
	/*** USER_LOCK 1未锁定*/
	public static final Integer USER_LOCK_NO = 1;
	/*** USER_LOCK 0锁定*/
	public static final Integer USER_LOCK_YES = 0;
	/*** 不对匹配该值的访问路径拦截（正则） */
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(websocket)).*";
	/*** 当前人员sessionkey */
	public static final String SESSION_USER = "CURRENT_SESSION_USER";
	public static final String INDEX = "/index";
	/*** 没有访问权限时 跳转的地址 */
	public static final String ACCESS_DENIED = "/UI/accessdenied.jsp";
	/*** 系统设置 跳转的地址 */
	public static final String PAGE_500 = "/UI/500.html";
	/*** 系统设置 跳转的地址 */
	public static final String PAGE_404 = "/UI/404.html";
	/*** 用户权限菜单 */
	public static final String USER_PERMISSION_URLS = "user_permission_urls";
	/*** 用户岗位 */
	public static final String USER_ROLES = "user_roles";
	/*** 强制退出key */
	public static final String SESSION_FORCE_LOGOUT_KEY = "session_force_logout";
	/*** 重复登录key */
	public static final String SESSION_REPEAT_LOGIN_KEY = "session_is_repeat_login";
	/*** 用户屏幕锁定 */
	public static final String USER_SCREEN_LOCK = "USER_SCREEN_LOCK";
	/*** 系统管理员专属验证角色字符串*/
	public static final String ADMIN_CHECK_STRING = "admin";
	/*** 系统管理员专属验证角色字符串*/
	public static final String ADMINISTRATOR_CHECK_STRING = "administrator";
	/*** 无权限code 401 */
	public static final String ACCESS_DENIED_CODE = "401";
	/*** 每个url的默认显示界面的地址 */
	public static final String PATH_DEFAULT = "default";
	/*** 默认码表ehcache缓存名称 */
	public static final String BASE_CODE_CACHE_NAME = "baseCodeCache";
	
	/*
	 * 短信配置对应的key
	 */
	public static final String MESSAGE_URL = "MESSAGE_URL";
	public static final String MESSAGE_SOAPACTION = "MESSAGE_SOAPACTION";
	public static final String MESSAGE_USERNAME = "MESSAGE_USERNAME";
	public static final String MESSAGE_USERPASSWORD = "MESSAGE_USERPASSWORD";
	public static final String MESSAGE_METHOD = "MESSAGE_METHOD";
	
	/**
	 * 数据权限对应key PERMISSIONS_DATAAUTHORITYS_MAP中的key
	 */
	public static final String DATA_AUTHORITY_SELECT = "DATA_AUTHORITY_SELECT";
	public static final String DATA_AUTHORITY_INSERT = "DATA_AUTHORITY_INSERT";
	public static final String DATA_AUTHORITY_DELETE = "DATA_AUTHORITY_DELETE";
	public static final String DATA_AUTHORITY_UPDATE = "DATA_AUTHORITY_UPDATE";
	
	/**
	 * 数据权限对应标志 用以查询 判断的标志
	 */
	public static final String DATE_AUTHORITY_TYPE_CREATE = "create";
	public static final String DATE_AUTHORITY_TYPE_DELETE = "delete";
	public static final String DATE_AUTHORITY_TYPE_QUERY = "query";
	public static final String DATE_AUTHORITY_TYPE_UPDATE = "update";
	
	/**
	 * session当中的保存数据权限map的对应key
	 */
	public static final Object PERMISSIONS_DATAAUTHORITYS_MAP = "PERMISSIONS_DATAAUTHORITYS_MAP";
	/**
	 * 系统主题key
	 */
	public static final String SYSTEM_THEME = "CURRENT_SYSTEM_THEME_ROOT";
}
 