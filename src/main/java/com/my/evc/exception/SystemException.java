package com.my.evc.exception;

public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;
    
    public SystemException(String errorCode,
            String errorMessage) {
        super(errorCode, errorMessage);
    }
}
