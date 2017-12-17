package com.my.evc.exception;

public class ReferenceException extends BaseException{

	private static final long serialVersionUID = 1L;

	public ReferenceException(String errorCode,
			String errorMessage) {
		super(errorCode, errorMessage);
	}
}
