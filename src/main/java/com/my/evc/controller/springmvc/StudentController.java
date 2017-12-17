package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.my.evc.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {
	
	private final Logger LOGGER = Logger.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<Student> createStudent(@RequestBody(required=true) Student student,
			HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		try {
			studentService.create(student);
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		return new JsonResponse<Student>(SUCCESS, student);
	}
	
	@ResponseBody
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public JsonResponse<String> deleteStudent(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		try {
			studentService.deleteByID(id);
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		return new JsonResponse<String>(SUCCESS, "Delete succeed!");
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public JsonResponse<String> updateStudent(@RequestBody(required=true) Student student,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		try {
			studentService.update(student);
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		return new JsonResponse<String>(SUCCESS, "Update succeed!");
	}
	
	@ResponseBody
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public JsonResponse<Student> findStudent(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		Student student = null;
		try {
			student = studentService.findByID(id);
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		return new JsonResponse<Student>(SUCCESS, student);
	}
	
}
