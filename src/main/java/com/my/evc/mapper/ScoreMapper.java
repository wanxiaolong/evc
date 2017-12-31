package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Score;

@MapperScan
public interface ScoreMapper extends BaseMapper<Score> {
	/**
	 * 批量插入成绩以提高数据库性能。<br>
	 * @param scoreList 待插入的成绩列表。
	 */
	public int createBatch(List<Score> scoreList);
}
