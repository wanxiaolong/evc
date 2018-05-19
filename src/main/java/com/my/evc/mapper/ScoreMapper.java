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
	 * 查询学生某学期考试的成绩（生日仅作为验证使用）。
	 */
	public List<ScoreVo> findBySemester(
			@Param("namePinYin") String namePinYin, 
			@Param("birthday") String birthday, 
			@Param("semesterId") int semesterId);
	
	/**
	 * 根据考试ID，查询所有的成绩（全班成绩）。
	 */
	public List<ScoreVo> findByClass(
			@Param("examId") int examId);

	/**
	 * 根据考试ID，删除所有的成绩。
	 */
	public void deleteByExam(
			@Param("examId") int examId);
}
