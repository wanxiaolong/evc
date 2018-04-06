package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Carousel;

@MapperScan
public interface CarouselMapper extends BaseMapper<Carousel> {
	/**
	 * 读取所有的轮播信息。
	 */
	public List<Carousel> findAll();
}
