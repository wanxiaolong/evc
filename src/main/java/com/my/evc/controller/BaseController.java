package com.my.evc.controller;

import com.my.evc.exception.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.ErrorEnum;
import com.my.evc.common.FailedResponse;

/**
 * 本类是所有控制器（Controller）的基类，这里定义了统一处理异常的方式，所以在子类中无需捕获异常了。
 */
@Controller
public class BaseController {

	protected static final int SUCCESS = 0;
	protected static final int FAILED = -1;
	protected static final String MODEL = "model";
	private static final Logger LOGGER = Logger.getLogger(BaseController.class);

	private FailedResponse getFailedResponse(ErrorEnum error) {
		FailedResponse failedResponse = new FailedResponse();
		failedResponse.setStatus(FAILED);
		failedResponse.setError(error);
		return failedResponse;
	}

	/**
	 * 处理DuplicateException。
	 */
	@ExceptionHandler(value = DuplicateException.class)
	@ResponseBody
	public FailedResponse duplicateExceptionHandler(DuplicateException exception) {
		LOGGER.error(exception.getErrorEnum().getDescription());
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理BusinessException。
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public FailedResponse baseExceptionHandler(BusinessException exception) {
		LOGGER.error(exception.getErrorEnum().getDescription(), exception);
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理ValidationException。
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public FailedResponse validationExceptionHandler(ValidationException exception) {
		LOGGER.error(exception.getErrorEnum().getDescription(), exception);
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理ReferenceException。
	 */
	@ExceptionHandler(value = ReferenceException.class)
	@ResponseBody
	public FailedResponse referenceExceptionHandler(ReferenceException exception) {
		LOGGER.error(exception.getErrorEnum().getDescription(), exception);
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理SystemException。
	 */
	@ExceptionHandler(value = SystemException.class)
	@ResponseBody
	public FailedResponse referenceExceptionHandler(SystemException exception) {
		LOGGER.error(exception.getErrorEnum().getDescription(), exception);
		return getFailedResponse(exception.getErrorEnum());
	}

	/**
	 * 处理Exception。
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public FailedResponse generalExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		return getFailedResponse(ErrorEnum.SYSTEM_ERROR);
	}
}