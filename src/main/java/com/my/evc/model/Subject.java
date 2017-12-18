package com.my.evc.model;

/**
 * 科目。
 */
public class Subject extends BaseModel {
	
	private String name;		//学生的主键
	private int adminId;		//管理员（创建者）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
}
