package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Student;
import com.my.evc.security.Permission;
import com.my.evc.security.RequirePermission;
import com.my.evc.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {
	
	@Autowired
	private StudentService studentService;

	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_ADD})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResponse<Student> createStudent(HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		Student student = getStudent(request);
		studentService.create(student);
		return new JsonResponse<Student>(SUCCESS, student);
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public JsonResponse<String> deleteById(HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		studentService.deleteByID(Integer.parseInt(id));
		return new JsonResponse<String>(SUCCESS, "Delete succeed!");
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_EDIT})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResponse<String> updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		Student student = getStudent(request);
		studentService.update(student);
		return new JsonResponse<String>(SUCCESS, "Update succeed!");
	}

	private Student getStudent(HttpServletRequest request) {
		String id = request.getParameter(Constant.PARAM_ID);
		String number = request.getParameter(Constant.PARAM_NUMBER);
		String name = request.getParameter(Constant.PARAM_NAME);
		String namePinyin = request.getParameter(Constant.PARAM_NAME_PINYIN);
		String sex = request.getParameter(Constant.PARAM_SEX);
		String grade = request.getParameter(Constant.PARAM_GRADE);
		String clazz = request.getParameter(Constant.PARAM_CLASS);
		String birthYear = request.getParameter(Constant.PARAM_BIRTH_YEAR);
		String birthDay = request.getParameter(Constant.PARAM_BIRTH_DAY);
		
		Student student = new Student();
		if (!StringUtils.isEmpty(id)) {
			student.setId(Integer.parseInt(id));
		}
		student.setNumber(Integer.parseInt(number));
		student.setName(name);
		student.setNamePinyin(namePinyin);
		student.setSex(sex);
		student.setGrade(grade);
		student.setClazz(clazz);
		student.setBirthYear(birthYear);
		student.setBirthDay(birthDay);
		return student;
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_VIEW})
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public JsonResponse<Student> findStudent(HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		Student student = studentService.findByID(Integer.parseInt(id));
		return new JsonResponse<Student>(SUCCESS, student);
	}
	
	/**
	 * 通过首字母来查找学生
	 */
	@ResponseBody
	@RequestMapping(value="/pinyin/{pinyin}", method = RequestMethod.GET)
	public JsonResponse<List<Student>> findByNamePinYin(@PathVariable("pinyin") String pinyin) 
			throws BaseException, Exception {
		List<Student> students = studentService.findByPinYin(pinyin);
		return new JsonResponse<List<Student>>(SUCCESS, students);
	}

	/**
	 * 查找所有学生
	 */
	@ResponseBody
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public JsonResponse<List<Student>> findByNamePinYin() 
			throws BaseException, Exception {
		List<Student> students = studentService.findAll();
		return new JsonResponse<List<Student>>(SUCCESS, students);
	}
}
