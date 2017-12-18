package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Exam;

@MapperScan
public interface ExamMapper extends BaseMapper<Exam> {
}
