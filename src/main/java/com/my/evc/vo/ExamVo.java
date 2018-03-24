package com.my.evc.vo;

import com.my.evc.model.Exam;

public class ExamVo extends Exam {
	private String semesterName;		//考试的学期名字
	
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
}
