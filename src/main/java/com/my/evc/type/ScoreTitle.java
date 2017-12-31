package com.my.evc.type;

/**
 * 上传成绩时，Excel的表头。
 */
public enum ScoreTitle {
	ID_NUMBER("学号"),
	CHINESE("语文"),
	MATH("数学"),
	ENGLISH("英语"),
	PHYSICS("物理"),
	CHEMISTRY("化学"),
	BIOLOGIC("生物"),
	POLITICS("政治"),
	HISTORY("历史"),
	GEOGRAPHY("地理"),
	PHYSICAL("体育"),
	EXPERIMENT("实验"),
	SAFE("安法");
	
	private String title;
	private ScoreTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	
	public static ScoreTitle fromString(String s) {
		for(ScoreTitle title : ScoreTitle.values()) {
			if (title.getTitle().equals(s)) {
				return title;
			}
		}
		return null;
	}
}
