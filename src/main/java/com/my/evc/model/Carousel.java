package com.my.evc.model;

/**
 * 描述一个轮播图片。 
 */
public class Carousel extends BaseModel {
	
	private String imgUrl;				//图片URL
	private String linkUrl;				//链接URL
	private int order;					//轮播顺序
	private String altText;				//图片的提示文本
	private boolean enabled;			//是否启用
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
