package com.my.evc.model;

import lombok.Data;
import lombok.ToString;

/**
 * 描述一个学期。 
 */
@Data
@ToString
public class Semester extends BaseModel {
	
	private String name;			//学期名称，比如：2017_2018年上学期
	private int number;				//学期编号，比如：20171,20172,20181（前四位代表学年，最后一位代表学期，1=上学期，2=下学期）
	private int year;				//年度

}
