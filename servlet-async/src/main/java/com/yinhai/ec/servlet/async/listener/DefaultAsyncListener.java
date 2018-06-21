package com.yinhai.ec.servlet.async.listener;

import java.io.IOException;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
* @package com.yinhai.ec.servlet.async.listener
* <p>Title: DefaultAsyncListener.java</p>
* <p>Description: 默认的servlet异步监听的实现</p>
* @author cjh
* @date 2016年4月26日 上午11:08:34
* @version 1.0
 */
@SuppressWarnings("rawtypes")
public class DefaultAsyncListener implements AsyncListener{

	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		
	}

	@Override
	public void onError(AsyncEvent event) throws IOException {
		AsyncContext asyncContext = event.getAsyncContext();  
        try {  
            String uri = (String) asyncContext.getRequest().getAttribute("uri");  
            Map params = (Map) asyncContext.getRequest().getAttribute("params");  
            LOG.error("async request error, uri : {}, params : {}", uri, JSONObject.toJSONString(params));  
        } catch (Exception e) {}
        try {
            HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();  
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
        } finally {
            asyncContext.complete();  
        }
	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		
	}
	
	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		AsyncContext asyncContext = event.getAsyncContext();  
        try {  
            String uri = (String) asyncContext.getRequest().getAttribute("uri");  
            Map params = (Map) asyncContext.getRequest().getAttribute("params");  
            LOG.error("async request timeout, uri : {}, params : {}", uri, JSONObject.toJSONString(params));  
        } catch (Exception e) {}  
        try {
            HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();  
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  
        } finally {  
            asyncContext.complete();  
        }
	}
	
	private final static Logger LOG = LoggerFactory.getLogger(DefaultAsyncListener.class);
}
