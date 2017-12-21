package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;
import com.my.evc.model.Student;

@MapperScan
public interface StudentMapper extends BaseMapper<Student> {
	/**
	 * 通过学号查找学生。
	 * @param idNumber 学号。
	 */
	public Student findByIdNumber(int idNumber);
}
