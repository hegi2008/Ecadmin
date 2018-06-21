package com.yinhai.ec.servlet.async.handler;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.ec.servlet.async.task.CanceledCallable;

/**
 * @package com.yinhai.ec.servlet.async.handler
 *          <p>
 *          Title: DefaultRejectHandler.java
 *          </p>
 *          <p>
 *          Description: servlet异步化,添加线程池队列失败默认处理
 *          </p>
 * @author cjh
 * @date 2016年4月26日 上午11:12:44
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class DefaultRejectHandler implements RejectedExecutionHandler {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultRejectHandler.class);

	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadpoolexecutor) {
		if (runnable instanceof CanceledCallable) {
			CanceledCallable cc = ((CanceledCallable) runnable);
			AsyncContext asyncContext = cc.asyncContext;
			if (asyncContext != null) {
				try {
					String uri = (String) asyncContext.getRequest().getAttribute("uri");
					Map params = (Map) asyncContext.getRequest().getAttribute("params");
					LOG.error("async request rejected, uri : {}, params : {}", uri, JSONObject.toJSONString(params));
				} catch (Exception e) {
				}
				try {
					HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} finally {
					asyncContext.complete();
				}
			}
		}
	}

}
