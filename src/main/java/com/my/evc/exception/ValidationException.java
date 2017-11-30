package com.my.evc.exception;

public class ValidationException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ValidationException(String errorCode,
            String errorMessage) {
        super(errorCode, errorMessage);
    }
}
