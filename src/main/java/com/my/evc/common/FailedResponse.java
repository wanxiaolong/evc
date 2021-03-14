package com.my.evc.common;

public class FailedResponse extends JsonResponse {
    private ErrorEnum error;

    public ErrorEnum getError() {
        return error;
    }

    public void setError(ErrorEnum error) {
        this.error = error;
    }
}