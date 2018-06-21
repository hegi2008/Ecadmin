package com.yinhai.ec.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;

/**
* @package com.yinhai.ec.base.util
* <p>Title: EasyuiUtil.java</p>
* <p>Description: EasyuiUtil</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年1月26日 下午3:41:31
* @version 1.0
 */
public class EasyuiUtil {
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method getMenuTreeJSON 方法 
	  * @describe <p>方法说明:根据menuslist返回Treejson</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年1月26日 下午3:42:51
	 */
	public static String getMenuTreeJSON(List<Map<String,Object>> menus,boolean isChildren) {
		if (CollectionUtils.isEmpty(menus)) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < menus.size(); i++) {
			Map<String, Object> menu = menus.get(i);
			if (!"0".equals(menu.get("parent_id").toString()) && !isChildren) {
				continue;
			}
			List<Map<String, Object>> children = findChildMenu(menu,menus);
			buffer.append("{");
			buffer.append("\"id\":"+menu.get("menu_id"));
			buffer.append(",\"text\":\""+menu.get("menu_name")+"\"");
			if (!isChildren) {
				buffer.append(",\"state\":\"open\"");
			}else{
				buffer.append(",\"parent_id\":"+menu.get("parent_id"));
				buffer.append(",\"menu_url\":\""+menu.get("menu_url")+"\"");
			}
			if (!CollectionUtils.isEmpty(children)) {
				if (isChildren) {
					buffer.append(",\"state\":\"open\"");
				}
				String json = getMenuTreeJSON(children,true);
				buffer.append(",\"children\":"+json);
			}
			buffer.append("}");
			buffer.append(",");
		}
		if (buffer.toString().endsWith(",")) {
			buffer = buffer.deleteCharAt(buffer.lastIndexOf(","));
		}
		buffer.append("]");
		return buffer.toString();
	}

	private static List<Map<String, Object>> findChildMenu(Map<String, Object> menu, List<Map<String, Object>> menus) {
		List<Map<String, Object>> children = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < menus.size(); i++) {
			Map<String, Object> map = menus.get(i);
			if (map.get("parent_id").toString().equals(menu.get("menu_id").toString())) {
				children.add(map);
			}
		}
		return children;
	}
}
