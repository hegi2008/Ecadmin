package com.yinhai.ec.base.service.online;  

import java.util.List;

import org.apache.shiro.session.Session;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.common.domain.OnlineDomain;
import com.yinhai.ec.common.domain.OnlineLogDomain;

/**
* @package com.yinhai.ec.base.service.online
* <p>Title: OnlineService.java</p>
* <p>Description: 用户在线管理</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-6 下午4:02:02
* @version 1.0
 */
public interface OnlineService extends BaseService{

	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method updateOnlineLogout 方法 
	  * @describe <p>方法说明:修改登录历史记录的登出时间</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	void updateOnlineLogout(OnlineLogDomain logDomain) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method addOnline 方法 
	  * @describe <p>方法说明:增加在线人员</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	void addOnline(OnlineDomain onlineDomain) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method addOnlineLog 方法 
	  * @describe <p>方法说明:记录用户登陆历史记录</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	void addOnlineLog(OnlineLogDomain logDomain) throws Exception;
	
	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method getOnlineLog 方法 
	  * @describe <p>方法说明:根据sessionid 以及 userid获取onlinelog</p>
	  * @return List<OnlineLogDomain> 
	  * @author cjh
	  * @date 2016-1-6
	 */
	List<OnlineLogDomain> getOnlineLogByUserIdAndSessionid(OnlineLogDomain logDomain);

	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method deleteOnlineByUserIdAndSessionid 方法 
	  * @describe <p>方法说明:删除在线用户</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-6
	 */
	void deleteOnlineByUserIdAndSessionid(OnlineDomain domain) throws Exception;

	/**
	  * @package com.yinhai.ec.base.service.online
	  * @method executeOnline 方法 
	  * @describe <p>方法说明:登陆后在线处理</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-11
	 */
	void executeOnline(Session session) throws Exception;
}
 