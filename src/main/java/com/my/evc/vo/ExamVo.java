package com.my.evc.vo;

import java.util.List;

import com.my.evc.model.Exam;

public class ExamVo extends Exam {
	private String semesterName;		//考试的学期名字
	private List<String> subjectNames;	//参考科目名称。父类Exam中有科目的id集合。
	
	public List<String> getSubjectNames() {
		return subjectNames;
	}
	public void setSubjectNames(List<String> subjectNames) {
		this.subjectNames = subjectNames;
	}
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
}
