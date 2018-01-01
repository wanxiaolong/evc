package com.my.evc.model;

/**
 * 描述一次考试。 <br>
 * 一次完整的考试应该描述为：<br>
 * 2017年度上学期英语半期考试 <br>
 * 2017年度上学期第3次语文考试
 */
public class Exam extends BaseModel {
	
	private int adminId;			//管理员ID（谁创建的这次考试）
	private String name;			//考试名称
	private int semesterNumber;		//学期。比如20171代表2017年上学期
	private boolean isHalf;			//是否是半期考试
	private boolean isFinal;		//是否是期末考试
	private String note;			//备注
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSemesterNumber() {
		return semesterNumber;
	}
	public void setSemesterNumber(int semesterNumber) {
		this.semesterNumber = semesterNumber;
	}
	public boolean isHalf() {
		return isHalf;
	}
	public void setHalf(boolean isHalf) {
		this.isHalf = isHalf;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
