package com.my.evc.model;

import lombok.Data;

/**
 * 描述一个轮播图片。 
 */
@Data
public class Carousel extends BaseModel {
	
	private String imgUrl;				//图片URL
	private String linkUrl;				//链接URL
	private int order;					//轮播顺序
	private String altText;				//图片的提示文本
	private boolean enabled;			//是否启用

}
