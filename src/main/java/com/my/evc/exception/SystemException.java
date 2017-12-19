package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public SystemException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
