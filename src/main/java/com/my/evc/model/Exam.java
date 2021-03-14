package com.my.evc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述一次考试。 <br>
 * 一次完整的考试应该描述为：<br>
 * 2017年度上学期英语半期考试 <br>
 * 2017年度上学期第3次语文考试
 */
@Data
@NoArgsConstructor
public class Exam extends BaseModel {
	
	private String name;				//考试名称
	private String subjectIds;			//参考科目ID，数组：[1,2,3]
	private int people;					//参考人数
	private String date;				//考试日期（格式：yyyy-MM-dd）
	private int semesterNumber;			//学期。比如20171代表2017年上学期
	private boolean isScoreUploaded;	//是否已经上传成绩
	private boolean isShowRank;			//是否显示单科排名
	private boolean isShowGradeRank;	//是否显示年级排名
	private boolean isShowClassRank;	//是否显示班级排名
	private String note;				//备注

	/**
	 * 构造函数
	 */
	public Exam(int id, String name, String subjectIds, int people, String date,
				int semesterNumber, boolean isShowRank, boolean isShowGradeRank, boolean isShowClassRank) {
		setId(id);
		this.name = name;
		this.subjectIds = subjectIds;
		this.people = people;
		this.date = date;
		this.semesterNumber = semesterNumber;
		this.isShowRank = isShowRank;
		this.isShowGradeRank = isShowGradeRank;
		this.isShowClassRank = isShowClassRank;
	}

}
