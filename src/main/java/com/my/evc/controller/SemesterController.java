package com.my.evc.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.BusinessException;
import com.my.evc.model.Semester;
import com.my.evc.service.SemesterService;

/**
 * 本类处理考试相关的请求。
 */
@Controller
@RequestMapping("/semester")
public class SemesterController extends BaseController {
	
	/** 学期名称的固定格式，将用于验证输入参数。 */
	private static final String SEMESTER_PATTERN = "[0-9]{4}~[0-9]{4}[上|下]学期";
	
	@Autowired
	private SemesterService semesterService;
	
	/**
	 * 查询所有的学期信息。
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Semester>> findBySemester(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<Semester> semesters = semesterService.findAll();
		return new JsonResponse<List<Semester>>(SUCCESS, semesters);
	}
	
	/**
	 * 删除学期。
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> deleteById(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		semesterService.deleteByID(Integer.parseInt(id));
		return new JsonResponse<Object>(SUCCESS, null);
	}

	/**
	 * 新增学期。
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> create(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		//用正则表达式验证学期名字是否符合“2016~2017上学期”这样的格式，不符合则直接拒绝
		String name = request.getParameter(Constant.PARAM_NAME);
		boolean isMatch = Pattern.matches(SEMESTER_PATTERN, name);
		if (!isMatch) {
			throw new BusinessException(ErrorEnum.INVALID_SEMESTER_NAME);
		}
		
		String yearString = name.substring(name.indexOf("~") + 1, name.indexOf("~") + 5);
		
		Semester semester = new Semester();
		semester.setName(name);
		semester.setYear(Integer.parseInt(yearString));
		semester.setNumber(Integer.parseInt(yearString + (name.contains("上") ? "1" : "2")));
		
		semesterService.create(semester);
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 修改学期。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> update(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		String name = request.getParameter(Constant.PARAM_NAME);
		
		Semester semester = new Semester();
		semester.setId(Integer.parseInt(id));
		semester.setName(name);
		semesterService.update(semester);
		return new JsonResponse<Object>(SUCCESS, null);
	}
}
