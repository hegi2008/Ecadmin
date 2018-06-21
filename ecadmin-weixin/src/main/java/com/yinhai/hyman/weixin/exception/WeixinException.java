package com.yinhai.hyman.weixin.exception;

/**
 * 微信API处理异常
 *
 * @author peiyu
 */
public class WeixinException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WeixinException() {
        super();
    }

    public WeixinException(String message) {
        super(message);
    }

    public WeixinException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeixinException(Throwable cause) {
        super(cause);
    }
}
