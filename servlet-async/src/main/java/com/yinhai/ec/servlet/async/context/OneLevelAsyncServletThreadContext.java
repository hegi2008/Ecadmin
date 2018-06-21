package com.yinhai.ec.servlet.async.context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.ec.servlet.async.handler.DefaultRejectHandler;
import com.yinhai.ec.servlet.async.listener.DefaultAsyncListener;
import com.yinhai.ec.servlet.async.task.CanceledCallable;

/**
* @package com.yinhai.hyman.base.util
* <p>Title: OneLevelAsyncServletThreadContext.java</p>
* <p>Description: servlet异步化 一级业务线程池</p>
* 保证在项目中是单例</br>
* 1/ spring sington </br>
* 2/ 使用ServletContextListener 让该业务线程池随着servlet容器一起启动 </br>
* 推荐spring的方式
* @author cjh
* @date 2016年4月26日 上午9:08:32
* @version 1.0
 */
public class OneLevelAsyncServletThreadContext implements InitializingBean, DisposableBean {
	
	public OneLevelAsyncServletThreadContext() {}
	
	/**
	  * @package com.yinhai.ec.servlet.async.context
	  * @method submitTask 方法 
	  * @describe <p>方法说明:开启异步请求,并将请求提交到一级业务线程池当中</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月26日 下午1:24:56
	 */
	public void submitTask(final HttpServletRequest req, final Callable<Object> task) {
        final String uri = req.getRequestURI();
        final Map<String, String[]> params = req.getParameterMap();
        
        if(!req.isAsyncSupported()){
			LOG.error("servlet not support AsyncContext, check your jdk and tomcat version, uri : {},  params : {}", uri, JSONObject.toJSONString(params));
			return;
		}
        
        // 开启异步上下文 在子线程中执行业务调用，并由其负责输出响应，主线程退出 
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.getRequest().setAttribute("uri", uri);
        asyncContext.getRequest().setAttribute("params", params);
        asyncContext.setTimeout(asyncTimeoutInSeconds * 1000);
        if(asyncListener != null) {
            asyncContext.addListener(asyncListener);
        }
        executor.submit(new CanceledCallable(asyncContext) { //提交任务给业务线程池
            @Override
            public Object call() throws Exception {
                Object o = task.call();  //业务处理调用
                if(o == null) {
                    callBack(asyncContext, o, uri, params);  //业务完成后，响应处理
                }else{
                	if(o instanceof String) {
                    	callBack(asyncContext, o, uri, params);
                    }else{
                    	callBack(asyncContext, JSONObject.toJSONString(o), uri, params);
                    }
                }
                return o;
            }
        });
    }
	
	/**
	  * @package com.yinhai.ec.servlet.async.context
	  * @method callBack 方法 
	  * @describe <p>方法说明:task处理完成,产生response相应</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年4月26日 下午1:31:11
	 */
	private void callBack(AsyncContext asyncContext, Object result, String uri, Map<String, String[]> params) {
        HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
        try {
            if(result instanceof String) {
            	sendJson(response, (String)result);
            } else {
            	sendJson(response, JSONObject.toJSONString(result));
            }
        } catch (Throwable e) {
        	LOG.error("get info error, uri : {},  params : {}", uri, JSONObject.toJSONString(params));
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //程序内部错误
        } finally {
            asyncContext.complete();
        }
    }
	
	private void sendJson(HttpServletResponse response,String json) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}
	
	/**
	  * 初始化一级业务线程池,是拥有无界队列+固定线程大小的池子
	  * @return void 
	  * @author cjh
	 */
	public void init() throws Exception {
		// TODO Auto-generated method stub
		String[] poolSizes = poolSize.split("-");  
	    //初始线程池大小  
	    int corePoolSize = Integer.valueOf(poolSizes[0]);  
	    //最大线程池大小  
	    int maximumPoolSize = Integer.valueOf(poolSizes[1]);  
	    queue = new LinkedBlockingQueue<Runnable>(queueCapacity);
	    executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeInMilliSeconds, TimeUnit.MILLISECONDS, queue, new DefaultThreadFactory(), new DefaultRejectHandler());
	    executor.allowCoreThreadTimeOut(true);
	  
	    if(asyncListener == null) {  
	        asyncListener = new DefaultAsyncListener();
	    }
	}
	
	@Override
	public void destroy() throws Exception {
		if(this.executor != null && !this.executor.isShutdown()){
			executor.shutdown();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	public AsyncListener getAsyncListener() {
		return asyncListener;
	}

	public void setAsyncListener(AsyncListener asyncListener) {
		this.asyncListener = asyncListener;
	}
	
	private final static Logger LOG = LoggerFactory.getLogger(OneLevelAsyncServletThreadContext.class);
	private final int queueCapacity = Integer.MAX_VALUE;
	private final String poolSize = "4-4";
	private final int keepAliveTimeInMilliSeconds = 0;
	private final int asyncTimeoutInSeconds = 10;
	private LinkedBlockingQueue<Runnable> queue;
	private ThreadPoolExecutor executor;
	private AsyncListener asyncListener;
	
	/**
	 * 自定义 ThreadFactory
	 */
	static class DefaultThreadFactory implements ThreadFactory{
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final String namePrefix;
		
		DefaultThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() :
                 Thread.currentThread().getThreadGroup();
			namePrefix = "servletAsync-level1-pool-" +
                    poolNumber.getAndIncrement() +
                   "-thread-";
		}
		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
			if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
			return null;
		}
		
	}
}
