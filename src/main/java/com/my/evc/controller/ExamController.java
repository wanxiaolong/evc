package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Exam;
import com.my.evc.model.Subject;
import com.my.evc.response.ExamResponse;
import com.my.evc.service.ExamService;
import com.my.evc.service.SubjectService;
import com.my.evc.util.DataUtil;
import com.my.evc.vo.ExamVo;

/**
 * 本类处理考试相关的请求。
 */
@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController {
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 通过一个学期号，查询这个学期下的所有考试。
	 */
	@RequestMapping(value = "/findBySemester", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Exam>> findBySemester(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String semesterId = request.getParameter(Constant.PARAM_SEMESTER_ID);
		List<Exam> exams = examService.findBySemester(Integer.parseInt(semesterId));
		return new JsonResponse<List<Exam>>(SUCCESS, exams);
	}

	/**
	 * 查找最近一次考试。
	 */
	@RequestMapping(value = "/findLast", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<ExamVo> findLastExam(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		ExamVo exam = examService.findLastExam();
		return new JsonResponse<ExamVo>(SUCCESS, exam);
	}
	
	/**
	 * 查找所有考试。
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<ExamResponse> findAll(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<Subject> subjects = subjectService.findAll();
		List<ExamVo> exams = examService.findAll();
		DataUtil.setSubjectNames(exams, subjects);
		ExamResponse data = new ExamResponse(exams, subjects);
		return new JsonResponse<ExamResponse>(SUCCESS, data);
	}

	/**
	 * 修改考试信息。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> updateExam(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String examId = request.getParameter(Constant.PARAM_ID);
		String name = request.getParameter(Constant.PARAM_NAME);
		String subjectIds = request.getParameter(Constant.PARAM_SUBJECT_IDS);
		String people = request.getParameter(Constant.PARAM_PEOPLE);
		String date = request.getParameter(Constant.PARAM_DATE);
		String isShowClassRank = request.getParameter(Constant.PARAM_SHOW_CLASS_RANK);
		String isShowGradeRank = request.getParameter(Constant.PARAM_SHOW_GRADE_RANK);
		String semesterId = request.getParameter(Constant.PARAM_SEMESTER_ID);
		Exam exam = new Exam(Integer.parseInt(examId), name, subjectIds, 
				Integer.parseInt(people), date, Integer.parseInt(semesterId), 
				strToBoolean(isShowGradeRank), strToBoolean(isShowClassRank));
		examService.update(exam);
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 创建考试。
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> createExam(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String name = request.getParameter(Constant.PARAM_NAME);
		String subjectIds = request.getParameter(Constant.PARAM_SUBJECT_IDS);
		String people = request.getParameter(Constant.PARAM_PEOPLE);
		String date = request.getParameter(Constant.PARAM_DATE);
		String isShowClassRank = request.getParameter(Constant.PARAM_SHOW_CLASS_RANK);
		String isShowGradeRank = request.getParameter(Constant.PARAM_SHOW_GRADE_RANK);
		String semesterId = request.getParameter(Constant.PARAM_SEMESTER_ID);
		//Create不需要ID，所以第一个参数默认为0
		Exam exam = new Exam(0, name, subjectIds, Integer.parseInt(people), date, Integer.parseInt(semesterId), 
				strToBoolean(isShowGradeRank), strToBoolean(isShowClassRank));
		examService.create(exam);
		
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	private boolean strToBoolean(String s) {
		return "是".equals(s) ? true : false;
	}
}
