package com.yinhai.ec.base.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.util.StringUtils;

/**
 * 该类已经添加了config参数名称常量， 通过getConfig(str)获取变量值时str可以传常量
 * 
 */
public class BaseConfigUtil {

	private final Properties p = new Properties();

	private static final BaseConfigUtil config = new BaseConfigUtil();

	private BaseConfigUtil() {
		init();
	}

	private void init() {
		try {
			p.load(BaseConfigUtil.class.getResourceAsStream("/config/ftp/config.properties"));
		} catch (IOException e) {
			System.out.println("找不到 config.properties 配置文件");
		}
	}

	/**
	 * 直接从config.properties中获取对应参数
	 * @param configProps
	 * @return
	 * @throws Exception
	 */
	public static String getConfig(String key) throws Exception {
		String value = config.p.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			System.out.println("没有找到相关配置项！");
		}
		return value;
	}

	/**
	 * 访问文件需要的根目录，加上文件的相对路径即可访问所需文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getFileServerDomain() throws Exception {
		return getConfig("fileserver.domain");
	}

	public static String getEcVisitPath() throws Exception {
		return getConfig("uploadpath.ec") + "/";
	}
	
	public static String getEcAlbumVisitPath() throws Exception {
		return getEcVisitPath() + getConfig("uploadpath.ec.album") +"/";
	}
	
	public static String getEcUserVisitPath() throws Exception {
		return getEcVisitPath() + getConfig("uploadpath.ec.user") +"/";
	}
	
	public static String getEcUserHeaderVisitPath() throws Exception {
		return getEcUserVisitPath() + getConfig("uploadpath.ec.user.header") +"/";
	}
	
	/**
	 * 获取上传路径 ftpserver.url 部分
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getFtpServerUrl() throws Exception {
		return getConfig("ftpserver.url");
	}
	
	/**
	 * 获取所有文件上传的根目录
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getRootUploadPath() throws Exception {
		return getConfig("uploadpath.root") + "/";
	}
	
	/**
	 * 获取EC框架上传图片根路劲
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getEcUploadPath() throws Exception {
		return getRootUploadPath() + getConfig("uploadpath.ec") + "/";
	}
	
	/**
	 * 获取EC框架上传图片用户路劲
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getEcUserUploadPath() throws Exception {
		return getEcUploadPath() + getConfig("uploadpath.ec.user")+ "/";
	}
	
	/**
	 * 获取EC框架上传图片用户头像路劲
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getEcUserHeaderUploadPath() throws Exception {
		return getEcUserUploadPath() + getConfig("uploadpath.ec.user.header")+ "/";
	}
	
	/**
	 * 获取EC框架上传图片相册的路劲
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getEcAlbumUploadPath() throws Exception {
		return getEcUploadPath() + getConfig("uploadpath.ec.album")+ "/";
	}
}
