package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Subject;

@MapperScan
public interface SubjectMapper extends BaseMapper<Subject> {
	/**
	 * 列出所有文件。
	 */
	public List<Subject> findByExamId(int examId);
	
	/**
	 * 查找所有科目（用于配置考试的科目的时候）。
	 */
	public List<Subject> listSubjects();
}
