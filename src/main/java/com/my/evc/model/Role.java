package com.my.evc.model;

import lombok.Data;

/**
 * 描述一个角色
 */
@Data
public class Role extends BaseModel {
	
	private int adminId;			//管理员ID
	private String name;			//角色名字
	private String permissions;		//权限列表，用Json数组保存，例如：空集合用[]表示

}
