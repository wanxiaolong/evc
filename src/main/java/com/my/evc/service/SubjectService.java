package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.SubjectMapper;
import com.my.evc.model.Subject;

@Service
@Transactional
public class SubjectService implements BaseService<Subject>{
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	public void create(Subject subject) throws BaseException {
		subjectMapper.create(subject);
	}

	public void deleteByID(int id) throws BaseException {
		subjectMapper.delete(id);
	}

	public void update(Subject subject) throws BaseException {
		subjectMapper.update(subject);
	}

	public Subject findByID(int id) throws BaseException {
		return subjectMapper.find(id);
	}
	

	/**
	 * 供文件列表页面展示。
	 */
	public List<Subject> findByExamId(int examId) throws BaseException {
		return subjectMapper.findByExamId(examId);
	}
	
	public List<Subject> findAll() throws BaseException {
		return subjectMapper.listSubjects();
	}
}
