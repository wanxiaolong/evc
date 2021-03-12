package com.my.evc.exception;

import com.my.evc.common.ErrorEnum;
import com.my.evc.common.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //处理业务异常
    @ExceptionHandler(BusinessException.class)
    public JsonResponse<ErrorEnum> customGenericExceptionHnadler(BusinessException exception){
        return new JsonResponse<ErrorEnum>(HttpStatus.BAD_REQUEST.value(), exception.getErrorEnum());
    }

    //处理其他异常
    @ExceptionHandler(Exception.class)
    public JsonResponse<ErrorEnum> customGenericExceptionHnadler(Exception exception){
        return new JsonResponse<ErrorEnum>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorEnum.SYSTEM_ERROR);
    }
}