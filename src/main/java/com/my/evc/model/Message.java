package com.my.evc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户在网站上的留言
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseModel {
	
	private String type;       //留言类型：问题question、建议suggestion、吐槽complain等
	private String nick;       //昵称
	private String contact;    //联系方式
	private String title;      //标题
	private String content;    //内容

}
