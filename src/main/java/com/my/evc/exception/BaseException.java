package com.my.evc.exception;

import com.my.evc.common.ErrorCodeAndMessage;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;
    private String errorCode = ErrorCodeAndMessage.DATABASE_ACCESS_FAILED_CODE;
    private String errorMessage = ErrorCodeAndMessage.DATABASE_ACCESS_FAILED_MESSAGE;

    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        //super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String errorCode, String errorMessage) {
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
