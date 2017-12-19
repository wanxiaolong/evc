package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	//Default error
	private ErrorEnum errorEnum = ErrorEnum.SYSTEM_ERROR;

	public BaseException(ErrorEnum errorEnum) {
		this.errorEnum = errorEnum;
	}
	
	public ErrorEnum getErrorEnum() {
		return errorEnum;
	}
	
	public void setErrorEnum(ErrorEnum errorEnum) {
		this.errorEnum = errorEnum;
	}
}
