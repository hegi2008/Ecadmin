package com.yinhai.ec.base.service.online.impl;  

import java.util.List;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.service.online.OnlineService;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.common.domain.OnlineDomain;
import com.yinhai.ec.common.domain.OnlineLogDomain;
import com.yinhai.ec.common.domain.UserDomain;
 
@Service
@Transactional
public class OnlineServiceImpl extends BaseServiceImpl implements OnlineService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void updateOnlineLogout(OnlineLogDomain logDomain) throws Exception {
		List<OnlineLogDomain> list = getOnlineLogByUserIdAndSessionid(logDomain);
		if(list.size() == 1){
			int i = sqlSession.update("hy.online.updateOnlineLogout", logDomain);
			if( i != 1){
				//throw new RuntimeException("修改登录历史记录的登出时间时,数量不为1,userid="+logDomain.getLogId()+";sessionid="+logDomain.getSessionid());
			}
		}
	}

	@Override
	public void addOnline(OnlineDomain onlineDomain) throws Exception {
		sqlSession.insert("hy.online.addOnline", onlineDomain);
	}

	@Override
	public void addOnlineLog(OnlineLogDomain logDomain) throws Exception {
		sqlSession.insert("hy.online.addOnlineLog", logDomain);
	}

	@Override
	public List<OnlineLogDomain> getOnlineLogByUserIdAndSessionid(OnlineLogDomain logDomain) {
		if(logDomain.getUserId() == null || logDomain.getSessionid() == null){
			return null;
		}
		return sqlSession.selectList("hy.online.getOnlineLogByUserIdAndSessionid", logDomain);
	}

	@Override
	public void deleteOnlineByUserIdAndSessionid(OnlineDomain domain)
			throws Exception {
		if(domain.getUserId() != null && domain.getSessionid() != null){
			int i = sqlSession.delete("hy.online.deleteOnlineByUserIdAndSessionid", domain);
			if (i != 1) {
				//throw new RuntimeException("修改登录历史记录的登出时间时,数量不为1,userid="+logDomain.getLogId()+";sessionid="+logDomain.getSessionid());
			}
		}
	}

	@Override
	public void executeOnline(Session session) throws Exception {
		UserDomain user = (UserDomain) session.getAttribute(HYConst.SESSION_USER);
		OnlineDomain onlineDomain = new OnlineDomain();
		OnlineLogDomain logDomain = new OnlineLogDomain();
		onlineDomain.setSessionid(session.getId().toString());
		onlineDomain.setUserId(user.getUserId());
		onlineDomain.setLoginIp(session.getHost());
		// 增加在线人员
		//addOnline(onlineDomain);
		//if (logger.isDebugEnabled()) {
			//logger.debug("用户:["+user.getUserName()+"]登陆,已加入在线人员列表");
		//}
		logDomain.setUserId(user.getUserId());
		logDomain.setSessionid(session.getId().toString());
		logDomain.setLoginIp(session.getHost());
		// 记录用户登陆历史记录
		addOnlineLog(logDomain);
		if (logger.isDebugEnabled()) {
			logger.debug("用户:["+user.getUserName()+"]登陆,已添加登陆历史记录");
		}
	}
	
}
 