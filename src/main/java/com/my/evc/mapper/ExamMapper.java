package com.my.evc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Exam;
import com.my.evc.vo.ExamVo;

@MapperScan
public interface ExamMapper extends BaseMapper<Exam> {
	/**
	 * 读取所有的考试信息。
	 */
	public List<ExamVo> findAll();
	
	/**
	 * 查询某个学期的所有考试信息。
	 */
	public List<Exam> findBySemester(@Param("semesterNumber") int semesterNumber);
	
	/**
	 * 查找最近一次考试信息
	 */
	public ExamVo findLastExam();
	
	/**
	 * 根据学期和考试名字查找，结果只有一个。
	 */
	public Exam findBySemesterAndName(
			@Param("semesterNumber") int semesterNumber,
			@Param("examName") String examName);
}
