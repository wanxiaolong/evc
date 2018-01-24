package com.my.evc.model;

/**
 * 描述管理员发布的一个公告，或通知。
 */
public class Notice extends BaseModel {
	
	private String userId;			//发布者ID，如果需要其详细信息，需要关联admin表
	private String userName;		//只是在公告列表页显示，插入Notice的时候不需要提供
	private String title;			//标题
	private String importantLevel;	//重要性（2=重要，1=一般，0=默认）
	private String content;			//内容
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImportantLevel() {
		return importantLevel;
	}
	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
