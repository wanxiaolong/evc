package com.my.evc.model;

/**
 * This is the base model class which contains some common attributes for
 * every database model. Note that this class is an <b>abstract</b> class, 
 * models are child class of it.
 */
public abstract class BaseModel {
	private int id;
	private String creationDate;

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
