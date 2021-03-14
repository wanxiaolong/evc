package com.my.evc.model;

import lombok.Data;

@Data
public class User extends BaseModel {
	
	private int roleId;				//角色ID，每个角色有不同的权限
	private String username;		//用户名
	private String password;		//密码
	private String email;			//邮箱（用于找回密码等）
	private String lastLogin;		//上次登录时间

}
