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
	
}
