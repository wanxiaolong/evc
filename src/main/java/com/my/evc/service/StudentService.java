package com.my.evc.service;

import com.my.evc.exception.BaseException;
import com.my.evc.model.Student;

public interface StudentService {
    public void createStudent(Student user) throws BaseException;
    public void deleteStudentByID(int id) throws BaseException;
    public void updateStudent(Student user) throws BaseException;
    public Student findStudentByID(int id) throws BaseException;
}
