package com.my.evc.type;

/**
 * 上传成绩时，Excel的固定表头。
 */
public enum FixedColumn {
	NUMBER("学号",0),
	NAME("姓名",1),
	BIRTH_DAY("生日",2),
	GRADE("年级",3),
	CLAZZ("班级",4);
	
	private String title;
	private int order;
	private FixedColumn(String title, int order) {
		this.title = title;
		this.order = order;
	}
	public String getTitle() {
		return title;
	}
	public int getOrder() {
		return order;
	}
	
	public static FixedColumn fromString(String s) {
		for(FixedColumn title : FixedColumn.values()) {
			if (title.getTitle().equals(s)) {
				return title;
			}
		}
		return null;
	}
	
	public static FixedColumn fromOrder(int order) {
		for(FixedColumn title : FixedColumn.values()) {
			if (title.getOrder() == order) {
				return title;
			}
		}
		return null;
	}
}
