package com.my.evc.controller.springmvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Semester;
import com.my.evc.service.ScoreService;
import com.my.evc.service.SemesterService;
import com.my.evc.util.FileUtil;
import com.my.evc.util.ValidateCodeUtil;
import com.my.evc.vo.ScoreVo;

/**
 * 本类处理文件相关的请求。
 */
@Controller
@RequestMapping("/score")
public class ScoreController extends BaseController {
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private SemesterService semesterService;
	
	/**
	 * 成绩上传页面。这里预先读取所有的学期信息，并返回到页面。
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView uploadScorePage(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		//成绩上传页面默认填充好第一个下拉菜单：学期
		List<Semester> semesters = semesterService.findAll();
		ModelAndView mav = new ModelAndView("score_upload");
		mav.addObject(Constant.PARAM_SEMESTERS, semesters);
		return mav;
	}
	
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
	 * 跳转到成绩查询页面。这里预先读取所有的学期信息，并返回到页面。
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView queryScorePage(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		//成绩查询页面默认填充好第一个下拉菜单：学期
		List<Semester> semesters = semesterService.findAll();
		ModelAndView mav = new ModelAndView("score");
		mav.addObject(Constant.PARAM_SEMESTERS, semesters);
		return mav;
	}
	
	/**
	 * 执行成绩查询。本方法同时支持查询个人成绩和查询全班成绩。如果name未指定，则查询全班成绩。<br>
	 * 注意这里返回的是Json对象，非视图对象。
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<List<ScoreVo>> queryScore(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String queryType = request.getParameter(Constant.PARAM_QUERY_TYPE);
		String name = request.getParameter(Constant.PARAM_NAME);
		String birthday = request.getParameter(Constant.PARAM_BIRTHDAY);
		String examId = request.getParameter(Constant.PARAM_EXAM_ID);
		String verifyCode = request.getParameter(Constant.PARAM_VERIFY_CODE);
		String sessionVerifyCode = (String)request.getSession().getAttribute(Constant.PARAM_VERIFY_CODE);
		//先检验验证码
		//开发时关闭验证码校验功能
//		if (!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
//			throw new ValidationException(ErrorEnum.ILLEGAL_REQUEST_ERROR_VERIFY_CODE);
//		}
		List<ScoreVo> scoreVos = null;
		if ("class".equalsIgnoreCase(queryType)) {
			scoreVos = scoreService.queryScoreByClass(Integer.parseInt(examId));
		} else {
			scoreVos = scoreService.queryScoreByName(name, birthday, Integer.parseInt(examId));
		}
		
		return new JsonResponse<List<ScoreVo>>(SUCCESS, scoreVos);
	}
	
	/**
	 * 查询成绩时获取验证码。
	 */
	@RequestMapping(value = "/getcode", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getValidationCode(HttpServletRequest request, 
		HttpServletResponse response) throws BaseException, Exception {
		HttpSession session = request.getSession();
		char[] chars = ValidateCodeUtil.getCode();
		session.setAttribute(Constant.PARAM_VERIFY_CODE, String.copyValueOf(chars));
		return ValidateCodeUtil.getImage(chars);
	}
}
