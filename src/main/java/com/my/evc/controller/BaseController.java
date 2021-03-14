package com.my.evc.controller;

import com.my.evc.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.ErrorEnum;
import com.my.evc.common.FailedResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 本类是所有控制器（Controller）的基类，这里定义了统一处理异常的方式，所以在子类中无需捕获异常了。
 */
@Controller
@Slf4j
public class BaseController {

	protected static final int SUCCESS = 0;
	protected static final int FAILED = -1;
	protected static final String MODEL = "model";

	private FailedResponse getFailedResponse(ErrorEnum error) {
		FailedResponse failedResponse = new FailedResponse();
		failedResponse.setStatus(FAILED);
		failedResponse.setErrorCode(error.getCode());
		failedResponse.setErrorMsg(error.getDescription());
		return failedResponse;
	}

	/**
	 * 处理DuplicateException。
	 */
	@ExceptionHandler(value = DuplicateException.class)
	@ResponseBody
	public FailedResponse duplicateExceptionHandler(DuplicateException exception, HttpServletResponse response) {
		log.error(exception.getErrorEnum().getDescription());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理BusinessException。
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public FailedResponse baseExceptionHandler(BusinessException exception, HttpServletResponse response) {
		log.error(exception.getErrorEnum().getDescription(), exception);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理ValidationException。
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public FailedResponse validationExceptionHandler(ValidationException exception, HttpServletResponse response) {
		log.error(exception.getErrorEnum().getDescription(), exception);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理ReferenceException。
	 */
	@ExceptionHandler(value = ReferenceException.class)
	@ResponseBody
	public FailedResponse referenceExceptionHandler(ReferenceException exception, HttpServletResponse response) {
		log.error(exception.getErrorEnum().getDescription(), exception);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理SystemException。
	 */
	@ExceptionHandler(value = SystemException.class)
	@ResponseBody
	public FailedResponse referenceExceptionHandler(SystemException exception, HttpServletResponse response) {
		log.error(exception.getErrorEnum().getDescription(), exception);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理Exception。
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public FailedResponse generalExceptionHandler(Exception exception, HttpServletResponse response) {
		log.error(exception.getMessage(), exception);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return getFailedResponse(ErrorEnum.SYSTEM_ERROR);
	}
}