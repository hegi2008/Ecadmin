package com.yinhai.ec.system.controller;  

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSONArray;
import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.service.dataAuthority.SystemDataAuthorityService;
import com.yinhai.ec.base.util.EasyuiUtil;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AppCodeDomain;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
import com.yinhai.ec.system.service.RoleAuthorityService;
import com.yinhai.ec.system.service.RoleManagerService;
  
/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: RoleManagerController.java</p>
* <p>Description: 角色管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-12 下午3:08:42
* @version 1.0
 */
@Controller
@RequestMapping("system/role/roleManager")
public class RoleManagerController extends BaseController{
	@Autowired
	private RoleManagerService roleManagerService;
	@Autowired
	private RoleAuthorityService authService;
	@Autowired
	private SystemDataAuthorityService dataAuthority;
	@Autowired
	private RequestMappingHandlerMapping mappingHandlerMapping;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception{
		List<AppCodeDomain> role_type = getAppCodeByCodeString("role_type");
		map.put("role_type", role_type);
		return "/system/roleManager.jsp";
	}
	
	@RequestMapping("/createRole")
	public String createRole(@RequestParam(required=false,name="role_id") final Integer role_id,ModelMap map) throws Exception{
		if(role_id != null){
			Map<String, Object> role = roleManagerService.queryFullRole(role_id);
			map.put("role", role);
		}
		List<AppCodeDomain> role_type = getAppCodeByCodeString("role_type");
		map.put("role_types", role_type);
		return "/system/createRole.jsp";
	}
	
	@RequestMapping("/editDataAuthority")
	public String editDataAuthority(@RequestParam(required=true,name="role_id") final Integer role_id
			,@RequestParam(required=true,name="type") final String type, ModelMap map) throws Exception{
		
		List<Map<String, String>> systems = dataAuthority.getSystemDbTables();
		List<Map<String, String>> apps = dataAuthority.getAppDbTables();
		List<Map<String, String>> authoritys = dataAuthority.getDataAuthorityByRoleIdAndDataType(role_id, type);
		map.put("systems", systems);
		map.put("apps", apps);
		map.put("authoritys", JSONArray.toJSONString(authoritys));
		map.put("type", type);
		map.put("role_id", role_id);
		
		return "/system/dataAuthorityList.jsp";
	}
	
	
	@RequestMapping("/viewAllUsers")
	public String viewAllUsers(ModelMap map, HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		Object role_id = pageParam.get("role_id");
		if (role_id != null && !"".equals(role_id.toString())) {
			UserRolesDomain role = roleManagerService.querySingleRole(Integer.valueOf((String) role_id));
			map.put("role", role);
		}
		return "/system/roleUserManager.jsp";
	}
	
	@RequestMapping("/saveRoleDataAuthority")
	@ResponseBody
	public Object saveRoleDataAuthority(HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		dataAuthority.saveRoleDataAuthority(pageParam,bean);
		return bean;
	}
	
	/**
	  * @package com.yinhai.ec.base.system.controller
	  * @method queryAllRoleUsers 方法 
	  * @describe <p>方法说明:根据role_id查询未授该角色权限的人员</p>
	  * @return Object 
	  * @author cjh
	  * @date 2016年2月22日 上午10:43:51
	 */
	@RequestMapping("/queryAllRoleUsers")
	@ResponseBody
	public Object queryAllRoleUsers(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(pageParam.get("role_id") == null){
			return null;
		}
		roleManagerService.queryForPageWithDefault("hy.role.manager.queryListPageRoleUserManager", pageParam);
		return pageParam.toDatagridMap();
	}
	
	/**
	  * @package com.yinhai.ec.base.system.controller
	  * @method queryRoleUsers 方法 
	  * @describe <p>方法说明:根据role_id查询已授该角色权限的人员</p>
	  * @return Object 
	  * @author cjh
	  * @date 2016年2月22日 上午10:43:51
	 */
	@RequestMapping("/queryRoleUsers")
	@ResponseBody
	public Object queryRoleUsers(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		if(pageParam.get("role_id") == null){
			return null;
		}
		roleManagerService.queryForPageWithDefault("hy.role.manager.queryListPageRoleUser",pageParam);
		return pageParam.toDatagridMap();
	}
	
	//查询角色list
	@RequestMapping("/queryRoleList")
	@ResponseBody
	public Object queryRoleList(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		roleManagerService.queryUserRoleList(pageParam);
		return pageParam.toDatagridMap();
	}
	//保存role信息
	@RequestMapping("/saveRole")
	@ResponseBody
	public Object saveRole(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		UserDomain loginUser = getUserInfo();
		roleManagerService.saveRole(pageParam, bean, loginUser);
		return bean;
	}
	//更新角色信息
	@RequestMapping("/updateRole")
	@ResponseBody
	public Object updateRole(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		roleManagerService.updateRole(pageParam, bean);
		return bean;
	}
	//删除角色信息
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Object deleteRole(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		String role_id = pageParam.get("role_id").toString();
		Map<String, String> param = new HashMap<String, String>();
		param.put("role_id", role_id);
		roleManagerService.deleteRole(param,bean);
		return bean;
	}
	
	/**
	  * @package com.yinhai.ec.base.system.controller
	  * @method updateRoleUser 方法 
	  * @describe <p>方法说明:添加或者删除角色人员</p>
	  * @return Object 
	  * @author cjh
	  * @date 2016年2月22日 下午1:57:17
	 */
	@RequestMapping("/updateRoleUser")
	@ResponseBody
	public Object updateRoleUser(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		roleManagerService.updateRoleUser(pageParam, bean);
		return bean;
	}
	
	@RequestMapping("/viewRoleAuthorityDetail")
	public String roleDetail(ModelMap map, HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		UserRolesDomain role = authService.querySingleRole(pageParam);
		map.put("role", role);
		return "/system/roleAuthorityDetail.jsp";
	}
	
	@RequestMapping("/queryMenuAuthorityTree")
	@ResponseBody
	public Object queryMenuAuthorityTree(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		List<Map<String, Object>> menus = authService.queryMenusList(pageParam);
		String menujson = EasyuiUtil.getMenuTreeJSON(menus,false);
		return menujson;
	}
	
	@RequestMapping("/queryMenuAuthorityIds")
	@ResponseBody
	public Object queryMenuAuthorityIds(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		String ids = authService.queryMenuAuthorityIdsByRoleId(pageParam);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		return JSONArray.toJSONString(map);
	}
	
	@RequestMapping("/saveRoleAuthority")
	@ResponseBody
	public Object saveRoleAuthority(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
		authService.saveRoleAuthority(pageParam,bean,map);
		return bean;
	}
	
	@RequestMapping("/viewAuthorityList")
	public String viewAuthorityList(ModelMap map, HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		UserRolesDomain role = authService.querySingleRole(pageParam);
		map.put("role", role);
		List<Map<String, Object>> list = authService.queryAuthorityListByRoleId(pageParam);
		map.put("roleList",JSONArray.toJSONString(list));
		return "/system/roleAuthorityList.jsp";
	}
	
	@RequestMapping("/updateAuthorityOne")
	@ResponseBody
	public Object updateAuthorityOne(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		authService.updateAuthorityOne(pageParam,bean);
		return bean;
	}
 }
 