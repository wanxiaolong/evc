package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.ExamMapper;
import com.my.evc.model.Exam;

@Service
@Transactional
public class ExamService implements BaseService<Exam>{
	
	@Autowired
	private ExamMapper examMapper;
	
	public void create(Exam exam) throws BaseException {
		examMapper.create(exam);
	}

	public void deleteByID(int id) throws BaseException {
		examMapper.delete(id);
	}

	public void update(Exam file) throws BaseException {
		examMapper.update(file);
	}

	public Exam findByID(int id) throws BaseException {
		return examMapper.find(id);
	}
	
	/**
	 * 获取所有的考试。
	 */
	public List<Exam> findAll() throws BaseException {
		List<Exam> exams = examMapper.findAll();
		return exams;
	}
	
	/**
	 * 通过学期号，查询该学期的所有考试。
	 */
	public List<Exam> findBySemester(int semesterNumber) throws BaseException {
		List<Exam> exams = examMapper.findBySemester(semesterNumber);
		return exams;
	}
	
}