package com.yinhai.message.rabbitmq;

public class RabbitmqConnException extends RuntimeException{

	private static final long serialVersionUID = 3969963850252586119L;
	
	public RabbitmqConnException() {
		super();
	}
	public RabbitmqConnException(String message) {
        super(message);
    }
	public RabbitmqConnException(String message, Throwable cause) {
        super(message, cause);
    }
	public RabbitmqConnException(Throwable cause) {
        super(cause);
    }
}
