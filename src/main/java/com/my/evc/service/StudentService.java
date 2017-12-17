package com.my.evc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.StudentMapper;
import com.my.evc.model.Student;
import com.my.evc.service.StudentService;

@Service
@Transactional
public class StudentService implements BaseService<Student>{

	@Autowired
	private StudentMapper studentMapper;
	
	public void create(Student student) throws BaseException {
		studentMapper.create(student);
	}

	public Student findByID(int id) throws BaseException {
		return studentMapper.find(id);
	}

	public void deleteByID(int id) throws BaseException {
		studentMapper.delete(id);
	}

	public void update(Student student) throws BaseException {
		studentMapper.update(student);
	}

}
