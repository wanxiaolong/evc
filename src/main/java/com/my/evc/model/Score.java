package com.my.evc.model;

import lombok.Data;

/**
 * 描述考试成绩。
 * 字段score1和score2是预留字段，用于非主科考试的时候。
 * 比如哪天临时有个比赛，成绩也可以用这个统计。
 */
@Data
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

}
