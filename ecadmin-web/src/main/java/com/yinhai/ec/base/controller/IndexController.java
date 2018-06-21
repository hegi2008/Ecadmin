package com.yinhai.ec.base.controller;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinhai.ec.base.util.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.ec.base.service.user.UserService;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.common.domain.UserRolesDomain;
import com.yinhai.ec.common.util.FTPUtil;
import com.yinhai.ec.system.service.UserManagerService;

import javax.servlet.http.HttpServletRequest;

/**
* @package com.yinhai.ec.base.controller
* <p>Title: IndexController.java</p>
* <p>Description: 登陆验证成功后的入口</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2015-12-31 上午9:55:41
* @version 1.0
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private SystemPropertiesManager spm;
	
	@RequestMapping
	public String execute(ModelMap map) throws Exception {
		Subject subject = getShiroSubject();
		Session shiroSession = subject.getSession(false);
		UserDomain user = (UserDomain) shiroSession.getAttribute(HYConst.SESSION_USER);
		if (user == null) {
			String username = subject.getPrincipal()+"";
			if(!StringUtils.isEmpty(username)){
				user = userService.findUserByUsername(username);
				shiroSession.setAttribute(HYConst.SESSION_USER, user);
			}
		}
		map.put("user", user);
		map.put("imageUrl", BaseConfigUtil.getFileServerDomain());
		
		// 菜单
		List<UserRolesDomain> roles = userService.getUserRolesByUserId(user.getUserId());
		if(roles != null && roles.size() > 0){
			String roleString = getRoleString(roles);
			List<Map<String, Object>> list = userService.getUserPermissionUrlsByRoleString(roleString);
			List<Map<String, Object>> allMenus = getChildMenu(list);
			map.put("allMenus", allMenus);
		}
		String systemTheme = spm.getSystemTheme();
		return "/UI/themes/"+systemTheme+"/index.jsp";
	}

	/**
	  * @package com.yinhai.ec.base.controller
	  * @method getChildMenu 方法 
	  * @describe <p>方法说明:获取菜单上下级关系</p>
	  * @return List<Map<String,Object>> 
	  * @author cjh
	  * @date 2016-1-11
	 */
	private List<Map<String, Object>> getChildMenu(List<Map<String, Object>> list) {
		if(list == null || list.size() == 0){
			return new ArrayList<Map<String,Object>>();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			if ("0".equals(map.get("parent_id").toString())) {
				List<Map<String, Object>> child = findChild(list,map);
				map.put("child", child);
				result.add(map);
			}
		}
		return result;
	}

	private List<Map<String, Object>> findChild(List<Map<String, Object>> list, Map<String, Object> map) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map2 = list.get(i);
			if (map2.get("parent_id").equals(map.get("menu_id"))) {
				result.add(map2);
			}
		}
		return result;
	}
	
	private String getRoleString(List<UserRolesDomain> roles) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < roles.size(); i++) {
			UserRolesDomain role = roles.get(i);
			builder.append(role.getRoleId());
			if(i != roles.size()-1){
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method lockScreen 方法 
	  * @describe <p>方法说明:锁定屏幕</p>
	  * @return String 
	  * @author cjh
	  * @throws Exception 
	  * @date 2016年2月22日 下午3:49:15
	 */
	@RequestMapping("/lockScreen")
	public String lockScreen(ModelMap map) throws Exception {
		UserDomain user = getUserInfo();
		String systemTheme = spm.getSystemTheme();
		if (user != null) {
			map.put("user", user);
			map.put("imageUrl", BaseConfigUtil.getFileServerDomain());
			getCurrentSession().setAttribute(HYConst.USER_SCREEN_LOCK, true);
			return "/UI/themes/"+systemTheme+"/lockscreen.jsp";
		}else{
			return "redirect:/login";
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method isLockScreen 方法 
	  * @describe <p>方法说明:是否锁屏</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年2月22日 下午4:04:16
	 */
	@RequestMapping("/isLockScreen")
	@ResponseBody
	public Object isLockScreen(ModelMap map) throws Exception {
		ResultBean bean = getResultBean();
		if(getCurrentSession().getAttribute(HYConst.USER_SCREEN_LOCK) != null && (Boolean)getCurrentSession().getAttribute(HYConst.USER_SCREEN_LOCK)){
			// 锁定
			bean.setError(true);
		}
		return bean;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method unlockScreen 方法 
	  * @describe <p>方法说明:解锁屏幕</p>
	  * @return Object 
	  * @author cjh
	  * @date 2016年2月22日 下午4:09:58
	 */
	@RequestMapping("/unlockScreen")
	@ResponseBody
	public Object unlockScreen(ModelMap map, HttpServletRequest request) throws Exception {
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		if(StringUtils.isEmpty(pageParam.get("password"))){
			bean.setError(true);
			bean.setError_msg("密码不能为空");
		}else{
			UserDomain user = getUserInfo();
			if (ObjectUtils.isEmpty(user)) {
				return "redirect:/login";
			}else{
				String password = EndecryptUtils.md5Password(user.getUserLogin(), pageParam.getString("password"));
				if (!password.equals(user.getUserPwd())) {
					bean.setError(true);
					bean.setError_msg("密码不正确,请重新输入");
					bean.setFocus("password");
				}else{
					getCurrentSession().removeAttribute(HYConst.USER_SCREEN_LOCK);
				}
			}
		}
		return bean;
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method viewUserInfo 方法 
	  * @describe <p>方法说明:跳转到个人信息界面</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年2月23日 下午2:03:36
	 */
	@RequestMapping("/viewUserInfo")
	public String viewUserInfo(ModelMap map) throws Exception{
		UserDomain user = getUserInfo();
		String systemTheme = spm.getSystemTheme();
		if (user != null) {
			map.put("user", user);
			map.put("imageUrl", BaseConfigUtil.getFileServerDomain());
			return "/UI/themes/"+systemTheme+"/user_info.jsp";
		}else{
			return "redirect:/login";
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.controller
	  * @method uploadUserPic 方法 
	  * @describe <p>方法说明:上传用户头像</p>
	  * @return String 
	  * @author cjh
	  * @date 2016年2月23日 下午2:11:45
	 */
	@RequestMapping("/uploadUserPic")
	@ResponseBody
	public Object uploadUserPic(MultipartFile file, HttpServletRequest request) throws Exception{
		UserDomain user = getUserInfo();
		// 从数据库获取最新的user
		PageParam pageParam = getPageParam(request);
		pageParam.put("user_id", user.getUserId());
		user = userManagerService.querySingleUser(pageParam);
		if(user.getUserHeaderPic() != null){
			deleteUserHeaderIfHas(BaseConfigUtil.getEcUserHeaderUploadPath(),user.getUserHeaderPic());
		}
		ResultBean bean = getResultBean();
		String fileType = file.getContentType();
		// 图片类型验证
		if(FTPUtil.isImage(fileType)){
			String type = "."+fileType.substring(fileType.lastIndexOf("/")+1, fileType.length());
			String fileName = System.currentTimeMillis()+"_original"+type;
			String path = BaseConfigUtil.getEcUserHeaderUploadPath();
			if(FTPUtil.uploadFTPFile(file,fileName,path)){
				user.setUserHeaderPic(BaseConfigUtil.getEcUserHeaderVisitPath()+fileName);
				userService.updateUserInfo(user,bean);
				bean.setUploadedPath(user.getUserHeaderPic());
				getCurrentSession().setAttribute(HYConst.SESSION_USER, user);
			}else{
				bean.setError(true);
				bean.setError_msg("上传的文件:"+file.getOriginalFilename()+"失败");
			}
		}else{
			bean.setError(true);
			bean.setError_msg("上传的文件:"+file.getOriginalFilename()+"类型不符合要求");
		}
		return bean;
	}

	/**
	  * @package com.yinhai.ec.base.controller
	  * @method deleteUserHeaderIfHas 方法 
	  * @describe <p>方法说明:如果存在 就删除user原来的头像</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年2月24日 下午4:56:21
	 */
	private void deleteUserHeaderIfHas(String path, String userHeaderPic) {
		try {
			String fileName = null;
			if(userHeaderPic.contains("/")){
				fileName = userHeaderPic.substring(userHeaderPic.lastIndexOf("/")+1, userHeaderPic.length());
			}else{
				fileName = userHeaderPic;	
			}
			if(FTPUtil.connectServer()){
				if(FTPUtil.makeAndEnterDirectory(path)){
					if(FTPUtil.isExistFile(fileName)){
						FTPUtil.deleteFile(fileName);
					}
				}
			}
		} finally {
			FTPUtil.closeConnect();
		}
	}
	
	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public Object updateUserInfo(HttpServletRequest request) throws Exception{
		ResultBean bean = getResultBean();
		PageParam pageParam = getPageParam(request);
		UserDomain user = getUserInfo();
		if(!StringUtils.isEmpty(pageParam.get("userSex"))){
			user.setUserSex(Integer.valueOf(pageParam.getString("userSex")));
		}
		if(!StringUtils.isEmpty(pageParam.get("userPhone"))){
			user.setUserPhone(pageParam.getString("userPhone"));
		}
		userService.updateUserInfo(user,bean);
		bean.setSuccess_msg("修改成功");
		return bean;
	}
	
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public Object queryUserInfo() throws Exception{
		UserDomain user = getUserInfo();
		Map<String, Object> map = new HashMap<String,Object>();
		if(user != null){
			map.put("user", user);
			map.put("imageUrl", BaseConfigUtil.getFileServerDomain());
		}else{
			return "redirect:/login";
		}
		return JSONObject.toJSONString(map);
	}
}
 