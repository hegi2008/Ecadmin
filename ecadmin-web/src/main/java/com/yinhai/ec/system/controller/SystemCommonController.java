package com.yinhai.ec.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.common.domain.OrgDomain;
import com.yinhai.ec.common.util.ZtreeJsonUtils;
import com.yinhai.ec.system.service.OrgManagerService;

/**
* @package com.yinhai.ec.system.controller
* <p>Title: SystemCommonController.java</p>
* <p>Description: 公共Controller类 用于前台各种插件需要查询后台json数据</p>
* @author cjh
* @date 2016年4月27日 下午4:24:16
* @version 1.0
 */
@RestController
@RequestMapping("system/common")
public class SystemCommonController extends BaseController{
	@Autowired
    private OrgManagerService orgService;
	
	/**
	  * @package com.yinhai.ec.system.controller
	  * @method queryOrgJson 方法 
	  * @describe <p>方法说明:查询部门json列表</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月27日 下午4:26:39
	 */
	@RequestMapping("/queryOrgJson")
	public Object queryOrgJson(){
		List<OrgDomain> list = orgService.queryOrgList();
		return ZtreeJsonUtils.toZtreeJsonWithNamepath(list);
	}
}
