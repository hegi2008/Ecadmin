package com.yinhai.ec.common.util;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.ec.common.domain.OrgDomain;

/**
 * @author Tanqingping
 * @version 1.0
 * @package com.yinhai.ec.common.util
 * <p>Title: ZtreeJsonUtils.java</p>
 * <p>Description: 工具类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 贵州医美达</p>
 * @date 2016/4/26
 */
public class ZtreeJsonUtils {
   /**
     * @package com.yinhai.ec.common.util
     * @method toZtreeJsonWithNamepath 方法 
     * @describe <p>方法说明:将org列表转为ztreejson 并且包含每个部门的path信息</p>
     * @return JSONArray 
     * @author cjh
     * @date 2016年4月28日 上午8:58:07
    */
   public static JSONArray toZtreeJsonWithNamepath(List<OrgDomain> orgs) {
	   JSONArray json = new JSONArray();
	   if(!CollectionUtils.isEmpty(orgs)){
           for (OrgDomain org : orgs) {
               JSONObject jo = new JSONObject();
               jo.put("id", org.getOrgId());
               jo.put("pId", org.getParentOrgId());
               jo.put("name", org.getOrgName());
               jo.put("orgIdPath", org.getOrgIdPath());
               jo.put("orgNamePath", org.getOrgNamePath());
               jo.put("orgLevel", org.getOrgLevel());
               // 默认顶级部门展开
               if(org.getParentOrgId().intValue() == 0){
            	   jo.put("open", true);
               }
               
               json.add(jo);
           }
       }
	   return json;
   }
}