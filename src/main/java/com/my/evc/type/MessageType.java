package com.my.evc.type;

/**
 * 描述留言类型。
 */
public enum MessageType {
	QUESTION(1, "提问"),
	SUGGESTION(2, "建议"),
	COMPLAIN(3, "吐槽");
	
	private int value;
	private String name;
	
	private MessageType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	/**
	 * 从Int转换。
	 */
	public static MessageType fromValue(int value) {
		MessageType type = MessageType.QUESTION;
		switch (value) {
			case 1:
				break;
			case 2:
				type = MessageType.SUGGESTION;
				break;
			case 3:
				type = MessageType.COMPLAIN;
				break;
			default:
				break;
		}
		return type;
	}
}
