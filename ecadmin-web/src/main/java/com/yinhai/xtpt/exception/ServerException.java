package com.yinhai.xtpt.exception;

public class ServerException extends ClientException {
	private static final long serialVersionUID = -4498012300940922241L;

	public ServerException(String errorMessage) {
		super(errorMessage);
	}
}
