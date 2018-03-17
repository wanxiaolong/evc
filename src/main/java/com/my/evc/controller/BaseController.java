package com.my.evc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.ErrorEnum;
import com.my.evc.common.FailedResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.DuplicateException;
import com.my.evc.exception.ReferenceException;
import com.my.evc.exception.ValidationException;

/**
 * 本类是所有控制器（Controller）的基类，这里定义了统一处理异常的方式，所以在子类中无需捕获异常了。
 */
@Controller
public class BaseController {

	protected static final int SUCCESS = 0;
	protected static final int FAILED = -1;
	protected static final String EMPTY_JSON = "{}";
	protected static final String MODEL = "model";

	/**
	 * Get the property of the FailedResponse object.
	 * 
	 * @return Return a FailedResponse object.
	 */
	public FailedResponse getFailedResponse(int errorCode, String errorMessage) {
		FailedResponse failedResponse = new FailedResponse();
		failedResponse.setStatus(FAILED);
		failedResponse.setErrorCode(errorCode);
		failedResponse.setErrorMessage(errorMessage);
		return failedResponse;
	}
	
	public FailedResponse getFailedResponse(ErrorEnum errorEnum) {
		return getFailedResponse(errorEnum.getCode(), errorEnum.getMessage());
	}

	/**
	 * Handle the duplicate exception.
	 * 
	 * @param duplicateException
	 * @return Return a FailedResponse object that contains FAILED_STATUS,
	 *		 errorCode and errorMessage in json format.
	 */
	@ExceptionHandler(value = DuplicateException.class)
	@ResponseBody
	public FailedResponse duplicateExceptionHandler(
			DuplicateException duplicateException) {
		return getFailedResponse(duplicateException.getErrorEnum());
	}

	/**
	 * Handle the base exception.
	 * 
	 * @return Return a FailedResponse object that contains FAILED_STATUS,
	 *		 errorCode and errorMessage in json format.
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public FailedResponse baseExceptionHandler() {
		return getFailedResponse(ErrorEnum.DATABASE_ACCESS_FAILED);
	}

	/**
	 * Handle the validation exception.
	 * 
	 * @param validationException
	 * @return
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public FailedResponse validationExceptionHandler(
			ValidationException validationException) {
		return getFailedResponse(validationException.getErrorEnum());
	}

	/**
	 * Handle the reference exception.
	 * 
	 * @param referenceException
	 * @return
	 */
	@ExceptionHandler(value = ReferenceException.class)
	@ResponseBody
	public FailedResponse referenceExceptionHandler(
			ReferenceException referenceException) {
		return getFailedResponse(referenceException.getErrorEnum());
	}

	/**
	 * Handle the exception.
	 * 
	 * @param Exception
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public FailedResponse exceptionHandler(Exception exception) {
		return getFailedResponse(ErrorEnum.SYSTEM_ERROR);
	}
}