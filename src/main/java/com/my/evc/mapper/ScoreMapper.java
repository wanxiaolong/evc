package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Score;

@MapperScan
public interface ScoreMapper extends BaseMapper<Score> {
}
