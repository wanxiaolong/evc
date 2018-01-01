package com.my.evc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Exam;

@MapperScan
public interface ExamMapper extends BaseMapper<Exam> {
	/**
	 * 读取所有的考试信息。
	 */
	public List<Exam> findAll();
	
	/**
	 * 查询某个学期的所有考试信息。
	 */
	public List<Exam> findBySemester(@Param("semesterNumber") int semesterNumber);
}
