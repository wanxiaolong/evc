package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Semester;

@MapperScan
public interface SemesterMapper extends BaseMapper<Semester> {
	/**
	 * 读取所有的学期信息。
	 */
	public List<Semester> findAll();
}
