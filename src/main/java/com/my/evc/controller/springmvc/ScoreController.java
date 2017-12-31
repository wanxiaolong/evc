package com.my.evc.controller.springmvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.exception.BaseException;
import com.my.evc.service.ScoreService;
import com.my.evc.util.FileUtil;
import com.my.evc.vo.ScoreVo;

/**
 * 本类处理文件相关的请求。
 */
@Controller
@RequestMapping("/score")
public class ScoreController extends BaseController {
	
	@Autowired
	private ScoreService scoreService;
	
	/**
	 * 成绩上传。这里上传的是一个Excel文件，后台需要读取这个文件，并把成绩插入到score表中。
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadScore(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		//调用工具类处理文件上传请求
		List<Map<String,String>> scoreList = FileUtil.handleUploadScore(request, response);
		scoreService.uploadScore(scoreList);
		
		response.setStatus(Status.CREATED.getStatusCode());
		//由于前台是使用jQuery的ajax异步上传的，上传完成后必须返回一个JSON字符串，
		//否则前台页面会显示Unexpected end of JSON input.错误。这是jQuery的参数设定。参看help文档#3.
		return EMPTY_JSON;
	}
	
	/**
	 * 跳转到成绩查询页面。
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView queryScorePage(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		ModelAndView mav = new ModelAndView("score");
		return mav;
	}
	
	/**
	 * 执行成绩查询。注意这里返回的是VO对象，非模型对象。
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public ModelAndView queryScore(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String name = request.getParameter(Constant.PARAM_NAME);
		String birthday = request.getParameter(Constant.PARAM_BIRTHDAY);
		String examId = request.getParameter(Constant.PARAM_EXAM_ID);
		List<ScoreVo> scoreVos = scoreService.queryScoreByName(name, birthday, Integer.parseInt(examId));
		//返回到score页面
		ModelAndView mav = new ModelAndView("score");
		mav.addObject(MODEL, scoreVos);
		mav.addObject(Constant.PARAM_NAME, name);
		mav.addObject(Constant.PARAM_BIRTHDAY, birthday);
		mav.addObject(Constant.PARAM_EXAM_ID, examId);
		return mav;
	}
}
