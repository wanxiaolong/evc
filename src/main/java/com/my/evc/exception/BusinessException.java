package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
