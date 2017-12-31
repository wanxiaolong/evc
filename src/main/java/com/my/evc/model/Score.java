package com.my.evc.model;

/**
 * 描述考试成绩。
 * 字段score1和score2是预留字段，用于非主科考试的时候。
 * 比如哪天临时有个比赛，成绩也可以用这个统计。
 */
public class Score extends BaseModel {
	
	private int studentNumber;		//学生的学号
	private int examId;				//考试的主键
	private double chinese;			//语文
	private double math;			//数学
	private double english;			//英语
	private double physics;			//物理
	private double chemistry;		//化学
	private double biologic;		//生物
	private double politics;		//政治
	private double history;			//历史
	private double geography;		//地理
	private double physical;		//体育
	private double experiment;		//实验
	private double score1;			//预留字段1
	private double score2;			//预留字段2
	
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
	public double getChinese() {
		return chinese;
	}
	public void setChinese(double chinese) {
		this.chinese = chinese;
	}
	public double getMath() {
		return math;
	}
	public void setMath(double math) {
		this.math = math;
	}
	public double getEnglish() {
		return english;
	}
	public void setEnglish(double english) {
		this.english = english;
	}
	public double getPhysics() {
		return physics;
	}
	public void setPhysics(double physics) {
		this.physics = physics;
	}
	public double getChemistry() {
		return chemistry;
	}
	public void setChemistry(double chemistry) {
		this.chemistry = chemistry;
	}
	public double getBiologic() {
		return biologic;
	}
	public void setBiologic(double biologic) {
		this.biologic = biologic;
	}
	public double getPolitics() {
		return politics;
	}
	public void setPolitics(double politics) {
		this.politics = politics;
	}
	public double getHistory() {
		return history;
	}
	public void setHistory(double history) {
		this.history = history;
	}
	public double getGeography() {
		return geography;
	}
	public void setGeography(double geography) {
		this.geography = geography;
	}
	public double getPhysical() {
		return physical;
	}
	public void setPhysical(double physical) {
		this.physical = physical;
	}
	public double getExperiment() {
		return experiment;
	}
	public void setExperiment(double experiment) {
		this.experiment = experiment;
	}
	public double getScore1() {
		return score1;
	}
	public void setScore1(double score1) {
		this.score1 = score1;
	}
	public double getScore2() {
		return score2;
	}
	public void setScore2(double score2) {
		this.score2 = score2;
	}
}
