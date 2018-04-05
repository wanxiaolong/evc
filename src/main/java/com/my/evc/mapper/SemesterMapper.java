package com.my.evc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Semester;

@MapperScan
public interface SemesterMapper extends BaseMapper<Semester> {
	/**
	 * 读取所有的学期信息。
	 */
	public List<Semester> findAll();
	
	/**
	 * 按照学期名字查找。用于批量上传的时候，避免重复创建。
	 */
	public Semester findByName(@Param("semesterName") String semesterName);
}
