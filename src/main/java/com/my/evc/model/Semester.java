package com.my.evc.model;

/**
 * 描述一个学期。 
 */
public class Semester extends BaseModel {
	
	private String name;			//学期名称，比如：2017~2018年上学期
	private int number;				//学期编号，比如：20171,20172,20181（前四位代表学年，最后一位代表学期，1=上学期，2=下学期）
	private int year;				//年度
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Semester [name=" + name + ", number=" + number + ", year=" + year + ", id=" + getId() + "]";
	}
}
