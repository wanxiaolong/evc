package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.SemesterMapper;
import com.my.evc.model.Semester;

@Service
@Transactional
public class SemesterService implements BaseService<Semester>{
	
	@Autowired
	private SemesterMapper semesterMapper;
	
	public void create(Semester semester) throws BaseException {
		semesterMapper.create(semester);
	}

	public void deleteByID(int id) throws BaseException {
		semesterMapper.delete(id);
	}

	public void update(Semester file) throws BaseException {
		semesterMapper.update(file);
	}

	public Semester findByID(int id) throws BaseException {
		return semesterMapper.find(id);
	}
	
	/**
	 * 获取所有的学期。
	 */
	public List<Semester> findAll() throws BaseException {
		List<Semester> exams = semesterMapper.findAll();
		return exams;
	}
}
