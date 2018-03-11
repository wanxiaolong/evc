package com.my.evc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.BusinessException;
import com.my.evc.exception.DaoException;
import com.my.evc.mapper.ScoreMapper;
import com.my.evc.model.Score;
import com.my.evc.type.ScoreTitle;
import com.my.evc.vo.ScoreVo;

@Service
@Transactional
public class ScoreService implements BaseService<Score> {
	
	private static final Logger LOGGER = Logger.getLogger(ScoreService.class);
	
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
	
	/**
	 * 将列表中的成绩插入到数据库中。<br>
	 * 由于之前的逻辑已经保证scoreList不会为空，所以这里无需再次验证。
	 */
	public int uploadScore(List<Map<String, String>> scoreList) throws DaoException, BusinessException {
		//取得最后一个Map，里面封装了examId，读取之后需要移除这个装examId的Map。
		Map<String, String> parameters = scoreList.get(scoreList.size() - 1);
		String examId = parameters.get(Constant.PARAM_EXAM_ID);
		scoreList.remove(parameters);
		
		//将读取到的成绩转换成Score对象并保存到List中
		List<Score> scores = new ArrayList<Score>();
		for(Map<String, String> map : scoreList) {
			Score score = new Score();
			score.setExamId(Integer.parseInt(examId));
			for(String key : map.keySet()) {
				ScoreTitle title = ScoreTitle.fromString(key.toUpperCase());
				if (title != null) {
					String value = map.get(key);
					saveValueToScore(title, value, score);
				}
			}
			scores.add(score);
		}
		
		//把成绩List保存在数据库中
		int rows = scoreMapper.createBatch(scores);
		//插入完成后验证插入的行数
		if (rows != scores.size()) {
			LOGGER.error("插入成绩数不完全。待插入：" + scores.size() + ", 实际插入：" + rows);
			throw new BusinessException(ErrorEnum.DAO_PARTIAL_INSERT);
		}
		LOGGER.info("插入数据库完成！已插入：" + rows);
		return rows;
	}
	
	/**
	 * 根据科目，将分值保存到Score对象上。
	 */
	private void saveValueToScore(ScoreTitle subjectType, String value, Score score) {
		switch (subjectType) {
			case ID_NUMBER:
				score.setStudentNumber(Integer.parseInt(value));
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

	/**
	 * 按姓名查询学生某次考试的成绩。
	 */
	public List<ScoreVo> queryScoreBySemester(String name, String birthday, int semesterId) {
		List<ScoreVo> scoreVos = scoreMapper.findBySemester(name, birthday, semesterId);
		//计算总分，由于有些成绩是等级，所以不能计算总分。
//		for(ScoreVo vo : scoreVos) {
//			double total = calculateTotal(vo);
//			vo.setTotal(total);
//		}
		return scoreVos;
	}
	
	/**
	 * 按姓名查询学生某次考试的成绩。
	 */
	public List<ScoreVo> queryScoreByClass(int examId) {
		List<ScoreVo> scoreVos = scoreMapper.findByClass(examId);
		//计算总分
//		for(ScoreVo vo : scoreVos) {
//			double total = calculateTotal(vo);
//			vo.setTotal(total);
//		}
		return scoreVos;
	}
	
	/**
	 * 计算一次考试的总分。
	 */
//	private double calculateTotal(ScoreVo vo) {
//		double sum = 0;
//		sum += Double.parseDouble(vo.getChinese());
//		sum += Double.parseDouble(vo.getMath());
//		sum += Double.parseDouble(vo.getEnglish());
//		sum += Double.parseDouble(vo.getPhysics());
//		sum += Double.parseDouble(vo.getChemistry());
//		sum += Double.parseDouble(vo.getBiologic());
//		sum += Double.parseDouble(vo.getPolitics());
//		sum += Double.parseDouble(vo.getHistory());
//		sum += Double.parseDouble(vo.getGeography());
//		sum += Double.parseDouble(vo.getPhysical());
//		sum += Double.parseDouble(vo.getExperiment());
//		sum += Double.parseDouble(vo.getScore1());
//		sum += Double.parseDouble(vo.getScore2());
//		return sum;
//	}

	/**
	 * 按学号查询学生某次考试的成绩。
	 * @param number 学号
	 * @param examId 考试
	 */
	public void queryScoreByNumber(int number, int examId) {
		
	}
}
