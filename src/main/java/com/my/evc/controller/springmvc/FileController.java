package com.my.evc.controller.springmvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.exception.BaseException;
import com.my.evc.model.File;
import com.my.evc.service.FileService;
import com.my.evc.util.FileUtil;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 文件上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		FileUtil.handleUploadFile(request, response);
		response.setStatus(Status.CREATED.getStatusCode());
		//由于前台是使用jQuery的ajax异步上传的，上传完成后必须返回一个JSON字符串，
		//否则前台页面会显示Unexpected end of JSON input.错误。这是jQuery的参数设定。参看help文档#3.
		return EMPTY_JSON;
	}
	
	/**
	 * 文件列表。
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listFiles(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		List<File> files = fileService.listFiles();
		ModelAndView mav = new ModelAndView("file");
		mav.addObject(MODEL, files);
		return mav;
	}
}
