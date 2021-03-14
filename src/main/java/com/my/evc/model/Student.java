package com.my.evc.model;

import lombok.Data;

/**
 * 描述一个学生对象。
 */
@Data
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

}
