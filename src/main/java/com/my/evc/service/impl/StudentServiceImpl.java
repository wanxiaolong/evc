package com.my.evc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.StudentMapper;
import com.my.evc.model.Student;
import com.my.evc.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    
    public void createStudent(Student student) throws BaseException {
        studentMapper.create(student);
    }

    public Student findStudentByID(int id) throws BaseException {
        return studentMapper.find(id);
    }

    public void deleteStudentByID(int id) throws BaseException {
        studentMapper.delete(id);
    }

    public void updateStudent(Student student) throws BaseException {
        studentMapper.update(student);
    }

}
