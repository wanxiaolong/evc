package com.my.evc.type;

/**
 * 本类描述公告模型（Notice）的重要级别。
 */
public enum NoticeImportantLevel {
	HIGH(2, "高"), MEDIUM(1, "一般"), DEFAULT(0, "默认");
	
	private int value;
	private String name;
	
	private NoticeImportantLevel(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public static NoticeImportantLevel fromValue(int value){
		NoticeImportantLevel importantLevel = NoticeImportantLevel.DEFAULT;
		switch (value) {
			case 2:
				importantLevel = HIGH;
				break;
			case 1:
				importantLevel = MEDIUM;
				break;
			default:
				break;
		}
		return importantLevel;
	}
}
