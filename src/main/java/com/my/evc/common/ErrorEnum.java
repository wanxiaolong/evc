package com.my.evc.common;

public enum ErrorEnum {
	SYSTEM_ERROR(500, "Internal server error!"),
	DAO_ERROR(100, "Database layer error."),
	DATABASE_ACCESS_FAILED(101, "Access DB failed."),
	SQL_ERROR(103, "SQL error"),
	
	
	INVALID_EXCEL_NO_SCORE(301, "无效Excel文件。没有成绩。"),
	INVALID_EXCEL_UNSUPPORTED_TYPE(302, "无效Excel文件。只支持.xlsx和.xls类型的文件。"),
	INVALID_EXCEL_EMPTY_FILE_NAME(303, "无效Excel文件。文件名为空。"),
	
	ILLEGAL_REQUEST_NO_EXAM_ID(401, "无效请求。参数中缺少exam_id"),
	ILLEGAL_REQUEST_ERROR_VERIFY_CODE(402, "验证码错误"),
	DAO_PARTIAL_INSERT(601, "只插入了部分数据"),
	
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
