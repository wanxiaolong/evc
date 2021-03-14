package com.my.evc.model;

/**
 * 描述管理员上传的文件对象。该文件可以是学习文件、或者成绩单。
 */
public class File extends BaseModel {
	
	private String type;			//类型（视频、音乐、文件、Excel、压缩文件等）
	private String name;			//文件名
	private String description;		//文件描述
	private int downloadCount;		//下载次数（用于统计热门下载的列表）
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
}
