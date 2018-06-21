package com.yinhai.ec.base.cache.ehcache;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.service.cache.BaseCodeCacheService;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.AppCodeDomain;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
* @package com.yinhai.ec.base.cache.ehcache
* <p>Title: AppCodeTemplate.java</p>
* <p>Description: 码表相关操作需要使用的类</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年2月2日 下午12:32:48
* @version 1.0
 */
public class AppCodeTemplate implements ApplicationContextAware{
	private BaseCodeCacheService codeCacheService;
	private EhCacheCacheManager cacheCacheManager;
	private static final Logger logger = LoggerFactory.getLogger(com.yinhai.ec.base.cache.ehcache.AppCodeTemplate.class);
	
	/**
	  * @package com.yinhai.ec.base.cache.ehcache
	  * @method init 方法 
	  * @describe <p>方法说明:初始化方法(暂未启用,码表默认是按需加载)</p>
	  * @return void 
	  * @author cjh
	 * @throws Exception 
	  * @date 2016年2月2日 下午12:37:27
	 */
	public void init() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("开始加载系统码表");
		}
		String codeString = codeCacheService.getGroupCodeString();
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		if(!StringUtils.isEmpty(codeString)){
			String[] codes = codeString.split(",");
			for (String code_string : codes) {
				List<AppCodeDomain> appCodeDomains = codeCacheService.getAppCodeByCodeString(code_string);
				Element element = new Element(code_string, appCodeDomains);
				if (cacheManager != null) {
					Cache cache = cacheManager.getCache(HYConst.BASE_CODE_CACHE_NAME);
					if (cache != null) {
						cache.put(element);
					}
				}
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("系统码表加载完成");
		}
	}

	/**
	  * @package com.yinhai.ec.base.cache.ehcache
	  * @method updateCodeCache 方法 
	  * @describe <p>方法说明:更新缓存中的码表 如果存在</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月11日 下午1:53:42
	 */
	public void updateCodeCache(String codeString) {
		if(com.yinhai.ec.common.util.StringUtils.isEmpty(codeString)){
			return;
		}
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		if(cacheManager != null){
			Cache cache = cacheManager.getCache(HYConst.BASE_CODE_CACHE_NAME);
			if(cache != null){
				if (cache.get(codeString) != null) {
					List<AppCodeDomain> codes = codeCacheService.getAppCodeByDb(codeString.toUpperCase(Locale.ENGLISH));
					Element element = new Element(codeString, codes);
					cache.put(element);
				}
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.cache.ehcache
	  * @method updateAllCodeCache 方法 
	  * @describe <p>方法说明: 刷新所有缓存中的码表. 此方法会影响性能</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月11日 下午2:10:58
	 */
	@SuppressWarnings("unchecked")
	public void updateAllCodeCache() {
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		if(cacheManager != null){
			Cache cache = cacheManager.getCache(HYConst.BASE_CODE_CACHE_NAME);
			if(cache != null){
				List<String> keys = cache.getKeys();
				Collection<Element> elements = new LinkedList<Element>();
				for (String key : keys) {
					 List<AppCodeDomain> codes = codeCacheService.getAppCodeByDb(key.toUpperCase(Locale.ENGLISH));
					 Element element = new Element(key, codes);
					 elements.add(element);
				}
				if(!elements.isEmpty()){
					cache.putAll(elements);
				}
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.cache.ehcache
	  * @method removeCodeFromCache 方法 
	  * @describe <p>方法说明:删除缓存中的码表 如果存在的话</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月12日 上午9:41:56
	 */
	public void removeCodeFromCache(String codeString) {
		if(com.yinhai.ec.common.util.StringUtils.isEmpty(codeString)){
			return;
		}
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		if(cacheManager != null){
			Cache cache = cacheManager.getCache(HYConst.BASE_CODE_CACHE_NAME);
			if(cache != null){
				if (cache.get(codeString) != null) {
					cache.remove(codeString);
				}
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.cache.ehcache
	  * @method clearCodeCache 方法 
	  * @describe <p>方法说明:清楚缓存中的所有码表</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月12日 上午9:44:15
	 */
	public void clearCodeCache() {
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		if(cacheManager != null){
			Cache cache = cacheManager.getCache(HYConst.BASE_CODE_CACHE_NAME);
			if(cache != null){
				if(cache.getKeys() != null && cache.getKeys().size() > 0){
					cache.removeAll();
				}
			}
		}
	}
	
	public List<AppCodeDomain> getAppCodeByCodeString(String code_string) throws Exception {
		return codeCacheService.getAppCodeByCodeString(code_string);
	}
	
	public String getCodeListJson(String code_string) throws Exception {
		if(StringUtils.isEmpty(code_string)){
			return null;
		}
		List<AppCodeDomain> codeDomains = getAppCodeByCodeString(code_string);
		return toCodeListJson(codeDomains);
	}
	
	
	private String toCodeListJson(List<AppCodeDomain> codeDomains) {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("[");
		if (codeDomains != null && codeDomains.size() > 0) {
			for (int i = 0; i < codeDomains.size(); i++) {
				AppCodeDomain code = codeDomains.get(i);
				stringbuilder.append("{\"id\":"+code.getCodeValue()+",");
				stringbuilder.append("\"name\":\""+code.getCodeValueName()+"\"");
				if(i != codeDomains.size() -1){
					stringbuilder.append("},");
				}else{
					stringbuilder.append("}");
				}
			}
		}
		stringbuilder.append("]");
		return stringbuilder.toString();
	}
	
	public BaseCodeCacheService getCodeCacheService() {
		return codeCacheService;
	}

	public void setCodeCacheService(BaseCodeCacheService codeCacheService) {
		this.codeCacheService = codeCacheService;
	}

	public EhCacheCacheManager getCacheCacheManager() {
		return cacheCacheManager;
	}

	public void setCacheCacheManager(EhCacheCacheManager cacheCacheManager) {
		this.cacheCacheManager = cacheCacheManager;
	}

	public static AppCodeTemplate getInstance() {
		return (AppCodeTemplate) applicationContext.getBean("appCodeTemplate");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppCodeTemplate.applicationContext = applicationContext;
	}
	
	private static ApplicationContext applicationContext;

}
