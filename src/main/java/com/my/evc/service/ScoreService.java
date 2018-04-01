package com.my.evc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.BusinessException;
import com.my.evc.exception.DaoException;
import com.my.evc.mapper.ExamMapper;
import com.my.evc.mapper.ScoreMapper;
import com.my.evc.mapper.SubjectMapper;
import com.my.evc.model.Exam;
import com.my.evc.model.Score;
import com.my.evc.model.Subject;
import com.my.evc.type.ScoreTitle;
import com.my.evc.util.ExcelUtil;
import com.my.evc.util.FileUtil;
import com.my.evc.vo.ScoreVo;

@Service
@Transactional
public class ScoreService implements BaseService<Score> {
	
	private static final Logger LOGGER = Logger.getLogger(ScoreService.class);
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private ExamMapper examMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;
	
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
	 * 处理成绩上传请求。上传的文件只能是.xls或.xlsx结尾的（只能是Excel文件），否则会报异常。<br>
	 * 系统会读取Excel中的数据，并返回一个成绩对象的列表。
	 * 
	 * @return List对象。 List里面是多个Map，每一个Map代表每个学生在某次考试的各科的成绩。
	 */
	public int uploadScore(String examId, FileItem fileItem) throws DaoException, BusinessException, IOException {
		//如果没有读取到考试信息，直接报错
		if (examId == null) {
			throw new BusinessException(ErrorEnum.ILLEGAL_REQUEST_NO_EXAM_ID);
		}
		//如果examId不是数字，直接报错
		try {
			Integer.parseInt(examId);
		} catch(Exception e) {
			throw new BusinessException(ErrorEnum.ILLEGAL_REQUEST_NO_EXAM_ID);
		}
		
		//检查Excel文件中的科目是否和系统一致
		Map<Integer, String> headerMap = FileUtil.handleFileItem(fileItem);
		if (!isExcelHeaderLegal(headerMap, examId)) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_EXAM_NOT_MATCH);
		}
		
		//读取Excel中的成绩
		List<Map<String, String>> scoreList = ExcelUtil.loadExcel(fileItem);
		
		//将读取到的成绩转换成Score对象并保存到List中
		List<Score> scores = new ArrayList<Score>();
		for(Map<String, String> map : scoreList) {
			Score score = new Score();
			score.setExamId(Integer.parseInt(examId));
			for(String key : map.keySet()) {
				ScoreTitle title = ScoreTitle.fromString(key.toUpperCase());
				if (title != null) {
					String scoreValue = map.get(key);
					saveScoreToSubject(title, scoreValue, score);
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
	 * 检查该Excel的表头是否符合要求（只检查配置的科目是否都按顺序在Excel中存在，Excel中多余的列并不检查）。
	 * @param headerMap Key=Excel的列索引，Value=Excel列名字
	 */
	public boolean isExcelHeaderLegal(Map<Integer, String> headerMap, String examId) throws BusinessException {
		//查找所有的科目信息，并将List转换成Map以便查找
		List<Subject> subjects = subjectMapper.findAll();
		Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();
		for (Subject subject : subjects) {
			subjectMap.put(subject.getId(), subject);
		}
		
		//查询数据库中该考试的科目情况
		Exam exam = null;
		try {
			exam = examMapper.find(Integer.parseInt(examId));
		} catch (DaoException e) {
			LOGGER.error("Find exam error", e);
		}
		if (exam == null) {
			throw new BusinessException(ErrorEnum.EXAM_NOT_FOUND);
		}
		
		//将该考试的科目ID转换成科目名字，然后挨个比较
		String[] subjectIdArray = exam.getSubjectIds().split(",");
		for (int i = 0; i < subjectIdArray.length; i++) {
			int configuredId = Integer.parseInt(subjectIdArray[i]);
			String configuredName = subjectMap.get(configuredId).getName();
			
			//Excel中前几列不是成绩，所以这里需要排除几个列
			String excelSubjectName = headerMap.get(i + 5);
			if (!configuredName.equalsIgnoreCase(excelSubjectName)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 根据科目，将分值保存到Score对象上。
	 */
	private void saveScoreToSubject(ScoreTitle subjectType, String value, Score score) {
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
			case TOTAL:
				score.setTotal(value);
				break;
			case SCORE1:
				score.setScore1(value);
				break;
			case SCORE2:
				score.setScore2(value);
				break;
			default:
				break;
		}
	}

	/**
	 * 按姓名查询学生某次考试的成绩。
	 */
	public List<ScoreVo> queryScoreBySemester(String namePinYin, String birthday, int semesterId) {
		List<ScoreVo> scoreVos = scoreMapper.findBySemester(namePinYin, birthday, semesterId);
		return scoreVos;
	}
	
	/**
	 * 按姓名查询学生某次考试的成绩。
	 */
	public List<ScoreVo> queryScoreByClass(int examId) {
		List<ScoreVo> scoreVos = scoreMapper.findByClass(examId);
		return scoreVos;
	}
	
	/**
	 * 按学号查询学生某次考试的成绩。
	 * @param number 学号
	 * @param examId 考试
	 */
	public void queryScoreByNumber(int number, int examId) {
		
	}
}
