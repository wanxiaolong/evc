package com.my.evc.vo;

import com.my.evc.model.Score;

/**
 * Score类的子类。此类为视图展示类，比模型类多了一些用于展示的字段。
 */
public class ScoreVo extends Score {
	private String semesterName;		//考试名称
	private String examName;			//考试名称
	private String studentName;			//学生姓名
	private double total;				//总分
	
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
