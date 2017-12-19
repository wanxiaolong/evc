package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class ValidationException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ValidationException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
