package com.my.evc.common;

public enum ErrorEnum {
	SYSTEM_ERROR(500, "Internal server error!"),
	DATABASE_ACCESS_FAILED(101, "Access DB failed."),
	USER_NOT_FOUND(102,"User not found.");
	
	private String message;
	private int code;
	
	private ErrorEnum(int code, String message) {
		this.message=message;
		this.code=code;
	}
	
	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}
}
