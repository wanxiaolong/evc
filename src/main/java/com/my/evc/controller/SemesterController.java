package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Semester;
import com.my.evc.service.SemesterService;

/**
 * 本类处理考试相关的请求。
 */
@Controller
@RequestMapping("/semester")
public class SemesterController extends BaseController {
	
	@Autowired
	private SemesterService semesterService;
	
	/**
	 * 查询所有的学期信息。
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Semester>> findBySemester(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<Semester> semesters = semesterService.findAll();
		return new JsonResponse<List<Semester>>(SUCCESS, semesters);
	}
}
