package com.my.evc.model;

/**
 * 描述考试成绩。
 * 字段score1和score2是预留字段，用于非主科考试的时候。
 * 比如哪天临时有个比赛，成绩也可以用这个统计。
 */
public class Score extends BaseModel {
	private int order;				//在excel中的顺序。因为按照等级排名后，原来的顺序被打乱了，需要通过这个字段恢复
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
	private String total;			//总分

	private int chineseRank;		//语文排名
	private int mathRank;			//数学排名
	private int englishRank;		//英语排名
	private int physicsRank;		//物理排名
	private int chemistryRank;		//化学排名
	private int biologicRank;		//生物排名
	private int politicsRank;		//政治排名
	private int historyRank;		//历史排名
	private int geographyRank;		//地理排名
	private int physicalRank;		//体育排名
	private int experimentRank;		//实验排名
	private int score1Rank;			//预留字段1排名
	private int score2Rank;			//预留字段2排名
	private int totalRank;			//总分排名
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getChineseRank() {
		return chineseRank;
	}
	public void setChineseRank(int chineseRank) {
		this.chineseRank = chineseRank;
	}
	public int getMathRank() {
		return mathRank;
	}
	public void setMathRank(int mathRank) {
		this.mathRank = mathRank;
	}
	public int getEnglishRank() {
		return englishRank;
	}
	public void setEnglishRank(int englishRank) {
		this.englishRank = englishRank;
	}
	public int getPhysicsRank() {
		return physicsRank;
	}
	public void setPhysicsRank(int physicsRank) {
		this.physicsRank = physicsRank;
	}
	public int getChemistryRank() {
		return chemistryRank;
	}
	public void setChemistryRank(int chemistryRank) {
		this.chemistryRank = chemistryRank;
	}
	public int getBiologicRank() {
		return biologicRank;
	}
	public void setBiologicRank(int biologicRank) {
		this.biologicRank = biologicRank;
	}
	public int getPoliticsRank() {
		return politicsRank;
	}
	public void setPoliticsRank(int politicsRank) {
		this.politicsRank = politicsRank;
	}
	public int getHistoryRank() {
		return historyRank;
	}
	public void setHistoryRank(int historyRank) {
		this.historyRank = historyRank;
	}
	public int getGeographyRank() {
		return geographyRank;
	}
	public void setGeographyRank(int geographyRank) {
		this.geographyRank = geographyRank;
	}
	public int getPhysicalRank() {
		return physicalRank;
	}
	public void setPhysicalRank(int physicalRank) {
		this.physicalRank = physicalRank;
	}
	public int getExperimentRank() {
		return experimentRank;
	}
	public void setExperimentRank(int experimentRank) {
		this.experimentRank = experimentRank;
	}
	public int getScore1Rank() {
		return score1Rank;
	}
	public void setScore1Rank(int score1Rank) {
		this.score1Rank = score1Rank;
	}
	public int getScore2Rank() {
		return score2Rank;
	}
	public void setScore2Rank(int score2Rank) {
		this.score2Rank = score2Rank;
	}
	public int getTotalRank() {
		return totalRank;
	}
	public void setTotalRank(int totalRank) {
		this.totalRank = totalRank;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
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
