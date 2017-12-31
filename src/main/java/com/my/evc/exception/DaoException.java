package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;

public class DaoException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public DaoException(ErrorEnum errorEnum) {
		super(errorEnum);
	}
}
