package com.my.evc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.exception.DaoException;
import com.my.evc.mapper.ScoreMapper;
import com.my.evc.model.Score;
import com.my.evc.type.ScoreTitle;

@Service
@Transactional
public class ScoreService implements BaseService<Score>{
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	public void create(Score score) throws BaseException {
		scoreMapper.create(score);
	}

	public void deleteByID(int id) throws BaseException {
		scoreMapper.delete(id);
	}

	public void update(Score score) throws BaseException {
		scoreMapper.update(score);
	}

	public Score findByID(int id) throws BaseException {
		return scoreMapper.find(id);
	}
	
	public List<Score> uploadScore(List<Map<String,String>> scoreList) throws DaoException {
		if (scoreList == null || scoreList.size() == 0) {
			System.out.println("空数组列表！");
			return null;
		}
		//取得最后一个Map，里面封装了examId
		Map<String, String> parameters = scoreList.get(scoreList.size() - 1);
		String examId = parameters.get("exam_id");
		scoreList.remove(parameters);
		
		List<Score> scores = new ArrayList<Score>();
		for(Map<String, String> map : scoreList) {
			Score score = new Score();
			score.setExamId(Integer.parseInt(examId));
			for(String key : map.keySet()) {
				ScoreTitle title = ScoreTitle.fromString(key.toUpperCase());
				if (title != null) {
					double value = Double.parseDouble(map.get(key));
					saveValueToScore(title, value, score);
				}
			}
			scores.add(score);
		}
		
		//把成绩保存在数据库中
		for(Score score: scores) {
			scoreMapper.create(score);
		}
		
		return scores;
	}
	
	/**
	 * 根据科目，将分值保存到Score对象上。
	 */
	private void saveValueToScore(ScoreTitle subjectType, Double value, Score score) {
		switch (subjectType) {
			case ID_NUMBER:
				score.setStudentIdNumber(value.intValue());
				break;
			case CHINESE:
				score.setChinese(value);
				break;
			case MATH:
				score.setMath(value);
				break;
			case ENGLISH:
				score.setEnglish(value);
				break;
			case PHYSICS:
				score.setPhysics(value);
				break;
			case CHEMISTRY:
				score.setChemistry(value);
				break;
			case BIOLOGIC:
				score.setBiologic(value);
				break;
			case POLITICS:
				score.setPolitics(value);
				break;
			case HISTORY:
				score.setHistory(value);
				break;
			case GEOGRAPHY:
				score.setGeography(value);
				break;
			case PHYSICAL:
				score.setPhysical(value);
				break;
			case EXPERIMENT:
				score.setExperiment(value);
				break;
			
			default:
				break;
		}
	}
}
