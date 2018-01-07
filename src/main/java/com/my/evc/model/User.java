package com.my.evc.model;

public class User extends BaseModel {
	
	private int roleId;				//角色ID，每个角色有不同的权限
	private String username;		//用户名
	private String password;		//密码
	private String email;			//邮箱（用于找回密码等）
	private String lastLogin;		//上次登录时间

	public String getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
