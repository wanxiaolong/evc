package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class DuplicateException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DuplicateException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
