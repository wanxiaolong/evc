package com.my.evc.response;

import java.util.List;

import com.my.evc.model.Subject;
import com.my.evc.vo.ExamVo;

public class ExamResponse {
	private List<ExamVo> exams;
	private List<Subject> subjects;
	
	public ExamResponse(List<ExamVo> exams, List<Subject> subjects) {
		this.exams = exams;
		this.subjects = subjects;
	}
	
	public List<ExamVo> getExams() {
		return exams;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setExams(List<ExamVo> exams) {
		this.exams = exams;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
}
