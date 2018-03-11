package com.my.evc.model;

/**
 * 描述考试成绩。
 * 字段score1和score2是预留字段，用于非主科考试的时候。
 * 比如哪天临时有个比赛，成绩也可以用这个统计。
 */
public class Score extends BaseModel {
	private int studentNumber;		//学生的学号
	private int examId;				//考试的主键
	private String chinese;			//语文
	private String math;			//数学
	private String english;			//英语
	private String physics;			//物理
	private String chemistry;		//化学
	private String biologic;		//生物
	private String politics;		//政治
	private String history;			//历史
	private String geography;		//地理
	private String physical;		//体育
	private String experiment;		//实验
	private String score1;			//预留字段1
	private String score2;			//预留字段2
	
	public int getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getChinese() {
		return chinese;
	}
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	public String getMath() {
		return math;
	}
	public void setMath(String math) {
		this.math = math;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getPhysics() {
		return physics;
	}
	public void setPhysics(String physics) {
		this.physics = physics;
	}
	public String getChemistry() {
		return chemistry;
	}
	public void setChemistry(String chemistry) {
		this.chemistry = chemistry;
	}
	public String getBiologic() {
		return biologic;
	}
	public void setBiologic(String biologic) {
		this.biologic = biologic;
	}
	public String getPolitics() {
		return politics;
	}
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	public String getPhysical() {
		return physical;
	}
	public void setPhysical(String physical) {
		this.physical = physical;
	}
	public String getExperiment() {
		return experiment;
	}
	public void setExperiment(String experiment) {
		this.experiment = experiment;
	}
	public String getScore1() {
		return score1;
	}
	public void setScore1(String score1) {
		this.score1 = score1;
	}
	public String getScore2() {
		return score2;
	}
	public void setScore2(String score2) {
		this.score2 = score2;
	}
}
