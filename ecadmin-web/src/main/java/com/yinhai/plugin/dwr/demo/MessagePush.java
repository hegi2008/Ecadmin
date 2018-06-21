package com.yinhai.plugin.dwr.demo;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.yinhai.plugin.dwr.model.BaseMessageModel;

/**
* @package com.yinhai.plugin.dwr.demo
* <p>Title: MessagePush.java</p>
* <p>Description: DWR 反向推送例子</p>
* @author cjh
* @date 2016年7月28日 上午11:50:31
* @version 1.0
 */
public class MessagePush extends BaseMessageModel{
	
	/**
	  * @package com.yinhai.plugin.dwr.demo
	  * @method onPageLoad 方法 
	  * @describe <p>方法说明:在打开jsp页面时,调用该方法,在ScriptSession中设定自己需要的值</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月28日 上午11:50:27
	 */
	@Override
	public void onPageLoad(final String key, final String value){
        //获取当前的ScriptSession
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        scriptSession.setAttribute(key, value);
	}
	
	/**
	  * @package com.yinhai.plugin.dwr.demo
	  * @method send2All 方法 
	  * @describe <p>方法说明:向所有ScriptSession客户端发送消息</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月28日 上午11:51:58
	 */
	public void send2All(final String content) {
		// 执行推送(全部scriptSession)
		Browser.withAllSessions(new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				// 设置要调用的 js及参数
				script.appendCall("showMessage", content);
				// 得到所有ScriptSession
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				// 遍历每一个ScriptSession
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	
	/**
	  * @package com.yinhai.plugin.dwr.demo
	  * @method send2User 方法 
	  * @describe <p>方法说明:向指定单个用户推送消息</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年7月28日 下午3:49:58
	 */
	public void send2User(final String userid, final String content) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession scriptsession) {
				if(scriptsession.getAttribute(SESSION_USER_ID) != null &&
						scriptsession.getAttribute(SESSION_USER_ID).toString().equals(userid)){
					return true;
				}
				return false;
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			@Override
			public void run() {
				script.appendCall("showMessage", content);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	
	private static final String SESSION_USER_ID = "session-user-id";
}
