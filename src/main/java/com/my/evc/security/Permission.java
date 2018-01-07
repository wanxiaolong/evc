package com.my.evc.security;

/**
 * 定义所有的权限。
 * 其余的类型，如果是Management结尾的，包含对于该模型的所有权限。
 */
public final class Permission {

	/**
	 * 管理员，拥有所有的权限。
	 */
	public static final int ADMIN = 1;
	
	/**
	 * 学生管理
	 */
	public static final int STUDENT_MANAGEMENT = 10;
	public static final int STUDENT_VIEW = 11;
	public static final int STUDENT_ADD = 12;
	public static final int STUDENT_EDIT = 13;
	public static final int STUDENT_DELETE = 14;
	
	/**
	 * 用户管理
	 */
	public static final int USER_MANAGEMENT = 20;
	public static final int USER_VIEW = 21;
	public static final int USER_ADD = 22;
	public static final int USER_EDIT = 23;
	public static final int USER_DELETE = 24;

	/**
	 * 角色管理
	 */
	public static final int ROLE_MANAGEMENT = 30;
	public static final int ROLE_VIEW = 31;
	public static final int ROLE_ADD = 32;
	public static final int ROLE_EDIT = 33;
	public static final int ROLE_DELETE = 34;
	
	/**
	 * 考试管理
	 */
	public static final int EXAM_MANAGEMENT = 40;
	public static final int EXAM_VIEW = 41;
	public static final int EXAM_ADD = 42;
	public static final int EXAM_EDIT = 43;
	public static final int EXAM_DELETE = 44;

	/**
	 * 文件管理
	 */
	public static final int FILE_MANAGEMENT = 50;
	public static final int FILE_VIEW = 51;
	public static final int FILE_ADD = 52;
	public static final int FILE_EDIT = 53;
	public static final int FILE_DELETE = 54;

	/**
	 * 成绩管理
	 */
	public static final int SCORE_MANAGEMENT = 60;
	public static final int SCORE_VIEW = 61;
	public static final int SCORE_ADD = 62;
	public static final int SCORE_EDIT = 63;
	public static final int SCORE_DELETE = 64;

	/**
	 * 公告管理
	 */
	public static final int NOTICE_MANAGEMENT = 70;
	public static final int NOTICE_VIEW = 71;
	public static final int NOTICE_ADD = 72;
	public static final int NOTICE_EDIT = 73;
	public static final int NOTICE_DELETE = 74;

	/**
	 * 留言管理
	 */
	public static final int MESSAGE_MANAGEMENT = 80;
	public static final int MESSAGE_VIEW = 81;
	public static final int MESSAGE_ADD = 82;
	public static final int MESSAGE_EDIT = 83;
	public static final int MESSAGE_DELETE = 84;

	private Permission() {
		super();
	}
}
