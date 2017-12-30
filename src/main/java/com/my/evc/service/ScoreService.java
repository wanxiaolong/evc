package com.my.evc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
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
	
	public void uploadScore(List<Map<String,String>> scoreList) {
		if (scoreList == null) {
			return;
		}
		for(Map<String, String> map : scoreList) {
			Score score = new Score();
			for(String key : map.keySet()) {
				ScoreTitle title = ScoreTitle.fromString(key.toUpperCase());
				if (title != null) {
					double value = Double.parseDouble(map.get(key));
					saveScore(title, value, score);
				}
			}
		}
	}
	
	/**
	 * 根据科目，将分值保存到Score对象上。
	 */
	private void saveScore(ScoreTitle subjectType, double value, Score score) {
		switch (subjectType) {
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
