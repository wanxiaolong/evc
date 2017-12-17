package com.my.evc.controller.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.ErrorCode;
import com.my.evc.common.FailedResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.DuplicateException;
import com.my.evc.exception.ReferenceException;
import com.my.evc.exception.ValidationException;

@Controller
public class BaseController {

	protected static final int SUCCESS = 0;
	protected static final int FAILED = -1;
	protected static final String EMPTY_SYMBOL = "{}";

	/**
	 * Get the property of the FailedResponse object.
	 * 
	 * @return Return a FailedResponse object.
	 */
	public FailedResponse getFailedResponse(String errorCode,
			String errorMessage) {
		FailedResponse failedResponse = new FailedResponse();
		failedResponse.setStatus(FAILED);
		failedResponse.setErrorCode(errorCode);
		failedResponse.setErrorMessage(errorMessage);
		return failedResponse;
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
		return getFailedResponse(duplicateException.getErrorCode(),
				duplicateException.getErrorMessage());
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
		return getFailedResponse(
				ErrorCode.DATABASE_ACCESS_FAILED_CODE,
				ErrorCode.DATABASE_ACCESS_FAILED_MESSAGE);
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
		return getFailedResponse(validationException.getErrorCode(),
				validationException.getErrorMessage());
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
		return getFailedResponse(referenceException.getErrorCode(),
				referenceException.getErrorMessage());
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
		return getFailedResponse(
				ErrorCode.SYSTEM_ERROR_CODE,
				ErrorCode.SYSTEM_ERROR_MESSAGE);
	}
}