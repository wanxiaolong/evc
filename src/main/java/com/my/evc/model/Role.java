package com.my.evc.model;

public class Role extends BaseModel {
	
	private int adminId;			//管理员ID
	private String name;			//角色名字
	private String permissions;		//权限列表，用Json数组保存，例如：空集合用[]表示

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
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
}
