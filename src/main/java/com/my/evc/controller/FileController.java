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
import com.my.evc.model.File;
import com.my.evc.service.FileService;
import com.my.evc.util.FileUtil;

/**
 * 本类处理文件相关的请求。
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 文件上传。
	 */
	//@RequirePermission(permissions = {Permission.FILE_ADD})
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Integer> uploadFile(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<String> fileNames = FileUtil.handleUploadFile(request);
		
		int count = fileService.createFiles(fileNames);
		
		//由于前台是使用jQuery的ajax异步上传的，上传完成后必须返回一个JSON字符串，
		//否则前台页面会显示Unexpected end of JSON input.错误。这是jQuery的参数设定。参看help文档#3.
		return new JsonResponse<Integer>(SUCCESS, count);
	}
	
	/**
	 * 删除文件。
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> deleteById(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		String name = request.getParameter(Constant.PARAM_NAME);//文件名
		
		//从数据库中删除记录
		fileService.deleteByID(Integer.parseInt(id));
		
		//从文件夹中删除文件
		FileUtil.deleteFile(name);
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 文件列表。
	 */
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<File>> listFiles(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		List<File> files = fileService.findAll();
		return new JsonResponse<List<File>>(SUCCESS, files);
	}
}
