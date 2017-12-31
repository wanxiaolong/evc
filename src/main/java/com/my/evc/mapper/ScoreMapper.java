package com.my.evc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Score;
import com.my.evc.vo.ScoreVo;

@MapperScan
public interface ScoreMapper extends BaseMapper<Score> {
	/**
	 * 批量插入成绩以提高数据库性能。<br>
	 * @param scoreList 待插入的成绩列表。
	 */
	public int createBatch(List<Score> scoreList);
	
	/**
	 * 查询学生某次考试的成绩（生日仅作为验证使用）。
	 */
	public List<ScoreVo> findByNameAndExam(
			@Param("name") String name, 
			@Param("birthday") String birthday, 
			@Param("examId") int examId);
}
