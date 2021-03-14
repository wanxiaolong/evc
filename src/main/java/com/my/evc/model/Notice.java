package com.my.evc.model;

import lombok.Data;

/**
 * 描述管理员发布的一个公告，或通知。
 */
@Data
public class Notice extends BaseModel {
	
	private int adminId;			//发布者ID，如果需要其详细信息，需要关联admin表
	private String userName;		//只是在公告列表页显示，插入Notice的时候不需要提供
	private String title;			//标题
	private String importantLevel;	//重要性（2=重要，1=一般，0=默认）
	private String content;			//内容
	
}
