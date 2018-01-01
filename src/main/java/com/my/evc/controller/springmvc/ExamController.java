package com.my.evc.controller.springmvc;

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
		String semesterNumber = request.getParameter(Constant.PARAM_SEMESTER);
		List<Exam> exams = examService.findBySemester(Integer.parseInt(semesterNumber));
		return new JsonResponse<List<Exam>>(SUCCESS, exams);
	}
}
