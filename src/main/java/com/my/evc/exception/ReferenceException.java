package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class ReferenceException extends BaseException{

	private static final long serialVersionUID = 1L;

	public ReferenceException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
