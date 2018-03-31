package com.my.evc.model;

/**
 * 这是所有模型的基类，这里定义了一些公共的方法或者属性。<br>
 * 需要注意的是，这个类是一个抽象类，不能实例化。
 */
public abstract class BaseModel {
	private int id;
	private String creationDate;

	public BaseModel(int id, String creationDate) {
		super();
		this.id = id;
		this.creationDate = creationDate;
	}
	
	public BaseModel() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(String creationDate) {
		if (creationDate.contains(".")) {
			creationDate = creationDate.substring(0, creationDate.lastIndexOf("."));
		}
		this.creationDate = creationDate;
	}

}
