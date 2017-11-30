package com.my.evc.exception;

public class DuplicateException extends BaseException {


    private static final long serialVersionUID = 1L;

    public DuplicateException(String errorCode,
            String errorMessage) {
        super(errorCode, errorMessage);
    }
}
