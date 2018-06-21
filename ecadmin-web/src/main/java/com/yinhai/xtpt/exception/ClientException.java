package com.yinhai.xtpt.exception;

public class ClientException extends Exception {
	private static final long serialVersionUID = -162450418032055057L;

	private String errMsg;
	
	public ClientException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
	}
	
	public ClientException(Throwable cause) {
		super(cause);
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
