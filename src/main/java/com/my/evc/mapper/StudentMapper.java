package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;
import com.my.evc.model.Student;

@MapperScan
public interface StudentMapper extends BaseMapper<Student> {
	public Student findByIdNumber(int idNumber);
}
