package com.my.evc.exception;

public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;
    
    public BusinessException(String errorCode,
            String errorMessage) {
        super(errorCode, errorMessage);
    }
}
