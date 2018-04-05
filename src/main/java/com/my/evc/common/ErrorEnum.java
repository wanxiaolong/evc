package com.my.evc.common;

public enum ErrorEnum {
	SYSTEM_ERROR(500, "系统内部错误"),
	DAO_ERROR(100, "数据库内部错误"),
	DATABASE_ACCESS_FAILED(101, "访问数据库出错"),
	SQL_ERROR(103, "SQL语法错误"),
	
	
	INVALID_EXCEL_NO_SCORE(301, "无效Excel文件。没有成绩。"),
	INVALID_EXCEL_UNSUPPORTED_TYPE(302, "无效Excel文件。只支持.xlsx和.xls类型的文件。"),
	INVALID_EXCEL_EMPTY_FILE_NAME(303, "无效Excel文件。文件名为空。"),
	INVALID_EXCEL_SUBJECT_NOT_MATCH(303, "无效Excel文件。科目和系统不符。"),
	INVALID_EXCEL_SUBJECT_NOT_EXIST(303, "无效Excel文件。Excel中该科目不存在。"),
	INVALID_ROLE(304, "无效的角色"),
	
	ILLEGAL_REQUEST_NO_EXAM_ID(401, "无效请求。参数中缺少exam_id"),
	INVALID_VERIFY_CODE(402, "无效验证码"),
	DAO_PARTIAL_INSERT(601, "只插入了部分数据"),
	
	INVALID_PASSWORD(105, "密码错误"),
	INVALID_PASSWORD_CONFIRM(106, "确认密码错误"),
	USER_NOT_FOUND(102,"没有该用户"),
	EXAM_NOT_FOUND(104,"没有找到考试信息");
	
	
	
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
