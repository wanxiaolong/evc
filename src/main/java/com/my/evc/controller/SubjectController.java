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
import com.my.evc.model.Subject;
import com.my.evc.service.SubjectService;

/**
 * 科目管理控制器。
 */
@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {
	
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 通过一个考试号，查询这个考试下的所有科目。
	 */
	@RequestMapping(value = "/findByExam", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Subject>> findByExamId(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String examId = request.getParameter(Constant.PARAM_EXAM_ID);
		List<Subject> subjects = subjectService.findByExamId(Integer.parseInt(examId));
		return new JsonResponse<List<Subject>>(SUCCESS, subjects);
	}

	/**
	 * 查询所有科目。
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Subject>> findAll(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<Subject> subjects = subjectService.findAll();
		return new JsonResponse<List<Subject>>(SUCCESS, subjects);
	}
	
}
