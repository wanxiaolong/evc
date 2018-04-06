package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.CarouselMapper;
import com.my.evc.model.Carousel;

@Service
@Transactional
public class CarouselService implements BaseService<Carousel> {

	@Autowired
	private CarouselMapper carouselMapper;
	
	public void create(Carousel carousel) throws BaseException {
		carouselMapper.create(carousel);
	}
	
	public void deleteByID(int id) throws BaseException {
		carouselMapper.delete(id);
	}

	public void update(Carousel carousel) throws BaseException {
		carouselMapper.update(carousel);
	}

	public Carousel findByID(int id) throws BaseException {
		return carouselMapper.find(id);
	}
	
	public List<Carousel> findAll() throws BaseException {
		return carouselMapper.findAll();
	}
}
