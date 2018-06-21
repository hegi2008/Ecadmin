package com.yinhai.ec.servlet.async.task;

import java.util.concurrent.Callable;

import javax.servlet.AsyncContext;

/**
* @package com.yinhai.hyman.base.util
* <p>Title: CanceledCallable.java</p>
* <p>Description: </p>
* @author cjh
* @date 2016年4月26日 上午10:38:06
* @version 1.0
 */
public class CanceledCallable implements Callable<Object>{
	public AsyncContext asyncContext;

	public CanceledCallable(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
