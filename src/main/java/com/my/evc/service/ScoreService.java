package com.my.evc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.BusinessException;
import com.my.evc.exception.DaoException;
import com.my.evc.mapper.ExamMapper;
import com.my.evc.mapper.ScoreMapper;
import com.my.evc.mapper.SemesterMapper;
import com.my.evc.mapper.StudentMapper;
import com.my.evc.mapper.SubjectMapper;
import com.my.evc.model.Exam;
import com.my.evc.model.Score;
import com.my.evc.model.Semester;
import com.my.evc.model.Student;
import com.my.evc.model.Subject;
import com.my.evc.type.FixedColumn;
import com.my.evc.type.ScoreTitle;
import com.my.evc.util.ExcelUtil;
import com.my.evc.util.FileUtil;
import com.my.evc.util.PinyinUtil;
import com.my.evc.vo.ScoreVo;

@Service
@Transactional
public class ScoreService implements BaseService<Score> {
	
	private static final Logger LOGGER = Logger.getLogger(ScoreService.class);
	
	/**
	 * 一个保存着所有科目的BiMap，Key是科目ID，Value是科目名称。使用BiMap是为了能方便的进行互相转换。
	 */
	private BiMap<String, String> subjectMap = HashBiMap.create();
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private ExamMapper examMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	@Autowired
	private SemesterMapper semesterMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
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
	 * @return 插入的行数。
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
		if (!isValidExcelHeader(headerMap, examId)) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_SUBJECT_NOT_MATCH);
		}
		//读取Excel中的数据，Excel中的一行就是这里的一个Map
		List<Map<String, String>> data = ExcelUtil.loadData(fileItem);
		
		//读取学生信息并插入到数据库中
		boolean saveStudent = true;
		if (saveStudent) {
			saveStudentForExam(data);
		}
		
		//读取成绩信息并插入到数据库中
		int rows = saveScoreForExam(examId, data);
		return rows;
	}

	/**
	 * 处理批量成绩上传请求。上传的文件只能是.zip，否则会报异常。<br>
	 * 系统会执行以下操作：
	 * 1. 把zip文件保存在临时目录
	 * 2. 解压zip文件
	 * 3. 根据文件夹名创建学期（如果学期不存在）
	 * 4. 根据Excel文件名创建考试，列名作为考试的科目信息，行数作为考试人数
	 * 5. 读取Excel中的数据并插入数据库
	 * 6. 返回插入失败的文件
	 * 
	 * @return 插入的行数。
	 */
	public List<String> uploadBatchScore(FileItem item) throws Exception {
		//1. 把zip文件保存在临时目录
		File zipFile = FileUtil.saveStreamToFile(item);
		
		//2. 解压zip文件
		FileUtil.unzip(zipFile);
		//zipPath: path/to/2018~2019上学期.zip
		String zipPath = zipFile.getPath();
		
		//extractedFolder代表解压后的文件夹，这是一个按“学期”命名的文件夹
		File extractedFolder = new File(zipPath.substring(0, zipPath.lastIndexOf(".zip")));
		
		//3. 根据文件夹名创建学期（如果学期不存在）
		String semesterName = extractedFolder.getName();
		Semester semester = semesterMapper.findByName(semesterName);
		if (semester == null) {
			semester = createSemester(semesterName);
		}
		
		//4. 根据Excel文件名创建考试。每一个scoreFile应该是一个Excel文件。
		initSubjects();
		List<String> failedFiles = new ArrayList<String>();
		for (File examScoreFile : extractedFolder.listFiles()) {
			if (examScoreFile.isFile()) {
				String examName = examScoreFile.getName();
				Exam exam = createExam(semester, examScoreFile);
				
				//5. 读取Excel中的数据并插入数据库。这里如果出错，也继续上传后续文件。不中断。
				List<Map<String, String>> scoreList = ExcelUtil.loadData(examScoreFile);
				try {
					saveScoreForExam(String.valueOf(exam.getId()), scoreList);
				} catch (BusinessException e) {
					LOGGER.error("上传成绩失败，文件名：" + examName, e);
					failedFiles.add(semesterName + File.separator + examName);
				}
			}
		}
		
		//5. 返回上传失败的文件列表
		return failedFiles;
	}
	
	/**
	 * 把读取好的成绩保存到数据库中，并返回相应的行数。
	 */
	private int saveStudentForExam(List<Map<String, String>> data) throws BusinessException {
		List<Student> students = new ArrayList<Student>();
		for(Map<String, String> map : data) {
			Student student = new Student();
			student.setNumber(Integer.parseInt(map.get("学号")));
			String name = map.get("姓名");
			student.setName(name);
			student.setNamePinyin(PinyinUtil.getFirstLetterInLowerCase(name));
			student.setBirthDay(map.get("生日"));
			student.setGrade(map.get("年级"));
			student.setClazz(map.get("班级"));
			students.add(student);
		}
		
		//把学生List保存在数据库中
		int rows = studentMapper.createBatch(students);
		//插入完成后验证插入的行数
		if (rows != students.size()) {
			LOGGER.error("插入成绩数不完全。待插入：" + students.size() + ", 实际插入：" + rows);
			throw new BusinessException(ErrorEnum.DAO_PARTIAL_INSERT);
		}
		LOGGER.info("学生信息插入完成！已插入：" + rows);
		return rows;
	}
	
	/**
	 * 把读取好的成绩保存到数据库中，并返回相应的行数。
	 */
	private int saveScoreForExam(String examId, List<Map<String, String>> scoreList) throws BusinessException {
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
	 * 根据Excel文件名创建考试，列名作为考试的科目信息，行数作为考试人数。
	 * @param examName 带后缀的Excel文件名，例如：abc.xlsx。
	 */
	private Exam createExam(Semester semester, File examScoreFile) throws DaoException, 
		FileNotFoundException, IOException, BusinessException {
		String examName = examScoreFile.getName();
		String dbExamName = examName.substring(0, examName.lastIndexOf(".xls"));
		
		//如果数据库中共有这个考试，则无需创建。管理员可在考试管理页面修改该考试的信息。
		Exam exam = examMapper.findBySemesterAndName(semester.getNumber(), dbExamName);
		if (exam != null) {
			return exam;
		} else {
			exam = new Exam();
		}
		
		Sheet sheet = ExcelUtil.getSheet0(examScoreFile);
		//读取Excel中的列数
		String subjectIds = getSubjectIdsByExam(sheet);
		
		exam.setName(dbExamName);
		//读取Excel中的数据行数，第一行为表头，不是成绩。所以有效行数要-1
		exam.setPeople(sheet.getPhysicalNumberOfRows() - 1);
		exam.setSemesterNumber(semester.getNumber());
		exam.setShowClassRank(false);//默认不显示排名
		exam.setShowGradeRank(false);
		exam.setSubjectIds(subjectIds);//读取Excel中的列数作为科目
		examMapper.create(exam);
		return exam;
	}

	/**
	 * 通过Excel文件的名字，获取准备创建的考试的科目ID集合。
	 * @return 代表该考试的科目ID的字符串：比如：1,2,3
	 */
	private String getSubjectIdsByExam(Sheet sheet) throws IOException, FileNotFoundException, BusinessException {
		String subjectIds = "";
		Map<Integer, String> headerMap = ExcelUtil.getHeaderRow(sheet);
		for (int i = 5; i < headerMap.size(); i++) {
			String subjectName = headerMap.get(i);
			if ("总分".equals(subjectName.trim())){
				continue;
			}
			String subjectId = subjectMap.inverse().get(subjectName);
			if (subjectId == null) {
				throw new BusinessException(ErrorEnum.INVALID_EXCEL_SUBJECT_NOT_EXIST);
			}
			subjectIds += subjectId + ",";
		}
		//去掉最后一个逗号
		if (subjectIds.length() > 1) {
			subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
		}
		return subjectIds;
	}

	/**
	 * 根据文件夹名字创建学期。
	 * @param name 文件夹名字，必须满足以下命名规范：2018~2019上学期
	 * @return 创建好的学期信息。
	 */
	private Semester createSemester(String name) throws DaoException {
		int index = name.indexOf("~") + 1;
		int year = Integer.parseInt(name.substring(index, index + 4));//2019
		int semesterNumber = Integer.parseInt(year + (name.contains("上") ? "1" : "2"));//20191

		Semester semester = new Semester();
		semester.setName(name);
		semester.setNumber(semesterNumber);
		semester.setYear(year);
		semesterMapper.create(semester);
		System.out.println("创建好的学期：" + semester);
		return semester;
	}
	
	/**
	 * 检查该Excel的表头是否符合要求（只检查配置的科目是否都按顺序在Excel中存在，Excel中多余的列并不检查）。
	 * @param headerMap Key=Excel的列索引，Value=Excel列名字
	 */
	private boolean isValidExcelHeader(Map<Integer, String> headerMap, String examId) throws BusinessException {
		//查找所有的科目信息，并将List转换成Map以便查找
		initSubjects();
		
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
		
		//成绩表前面都有几个固定的列，先检查它们是否存在，是否匹配
		int fixedColNum = FixedColumn.values().length;
		for (int i = 0; i < fixedColNum; i++) {
			String fixedColName = FixedColumn.fromOrder(i).getTitle();
			if (!fixedColName.equals(headerMap.get(i))) {
				throw new BusinessException(ErrorEnum.INVALID_EXCEL_INVALID_COLUMN);
			}
		}
		
		//将该考试的科目ID转换成科目名字，然后挨个比较
		String[] subjectIdArray = exam.getSubjectIds().split(",");
		for (int j = 0; j < subjectIdArray.length; j++) {
			String configuredName = subjectMap.get(subjectIdArray[j]);
			//Excel中前几列不是成绩，所以这里需要排除几个列
			String excelSubjectName = headerMap.get(j + fixedColNum);
			if (!configuredName.equalsIgnoreCase(excelSubjectName)) {
				return false;
			}
		}
		
		return true;
	}

	private void initSubjects() {
		subjectMap.clear();
		List<Subject> subjects = subjectMapper.findAll();
		for (Subject subject : subjects) {
			subjectMap.put(String.valueOf(subject.getId()), subject.getName());
		}
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
