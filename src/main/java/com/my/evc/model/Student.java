package com.my.evc.model;

/**
 * 描述一个学生对象。
 */
public class Student extends BaseModel {
	
	private int number;				//学号
	private String name;			//姓名
	private String namePinyin;		//姓名缩写（拼音首字母）
	private String sex;				//性别
	private String grade;			//年级
	private String clazz;			//班级
	private String birthYear;		//出生年份（用于计算年龄）
	private String birthDay;		//出生日期（用于查询成绩）
	private String description;		//备注
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNamePinyin() {
		return namePinyin;
	}
	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
