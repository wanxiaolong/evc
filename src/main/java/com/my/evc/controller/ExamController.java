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
import com.my.evc.service.ExamService;
import com.my.evc.vo.ExamVo;

/**
 * 本类处理考试相关的请求。
 */
@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController {
	
	@Autowired
	private ExamService examService;
	
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
	public JsonResponse<List<ExamVo>> findAll(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<ExamVo> exams = examService.findAll();
		return new JsonResponse<List<ExamVo>>(SUCCESS, exams);
	}

	/**
	 * 查找所有考试。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> updateExam(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String examId = request.getParameter(Constant.PARAM_ID);
		String name = request.getParameter(Constant.PARAM_NAME);
		String people = request.getParameter(Constant.PARAM_PEOPLE);
		String date = request.getParameter(Constant.PARAM_DATE);
		String isShowClassRank = request.getParameter(Constant.PARAM_SHOW_CLASS_RANK);
		String isShowGradeRank = request.getParameter(Constant.PARAM_SHOW_GRADE_RANK);
		
		Exam exam = createExamObj(examId, name, people, date, isShowClassRank, isShowGradeRank);
		examService.update(exam);
		
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	private Exam createExamObj(String examId, String name, String people, String date, String isShowClassRank, String isShowGradeRank) {
		Exam exam = new Exam();
		exam.setId(Integer.parseInt(examId));
		exam.setDate(date);
		exam.setName(name);
		exam.setPeople(Integer.parseInt(people));
		exam.setShowClassRank(strToBoolean(isShowClassRank.trim()));
		exam.setShowGradeRank(strToBoolean(isShowGradeRank.trim()));
		return exam;
	}
	
	private boolean strToBoolean(String s) {
		return "是".equals(s) ? true : false;
	}
}
