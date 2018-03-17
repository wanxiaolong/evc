package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<Student> createStudent(@RequestBody(required=true) Student student,
			HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		studentService.create(student);
		return new JsonResponse<Student>(SUCCESS, student);
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_DELETE})
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public JsonResponse<String> deleteStudent(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		studentService.deleteByID(id);
		return new JsonResponse<String>(SUCCESS, "Delete succeed!");
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_EDIT})
	@RequestMapping(method = RequestMethod.PUT)
	public JsonResponse<String> updateStudent(@RequestBody(required=true) Student student,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		studentService.update(student);
		return new JsonResponse<String>(SUCCESS, "Update succeed!");
	}
	
	@ResponseBody
	@RequirePermission(permissions = {Permission.STUDENT_VIEW})
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public JsonResponse<Student> findStudent(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		Student student = studentService.findByID(id);
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
