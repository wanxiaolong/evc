package com.my.evc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Score;
import com.my.evc.security.Permission;
import com.my.evc.security.RequirePermission;
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
	@RequirePermission(permissions = {Permission.SCORE_ADD})
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadScore(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		//调用工具类处理文件上传请求
		List<Map<String,String>> scoreList = FileUtil.handleUploadScore(request);
		scoreService.uploadScore(scoreList);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		//由于前台是使用jQuery的ajax异步上传的，上传完成后必须返回一个JSON字符串，
		//否则前台页面会显示Unexpected end of JSON input.错误。这是jQuery的参数设定。参看help文档#3.
		return EMPTY_JSON;
	}
	
	/**
	 * 执行成绩查询。本方法同时支持查询个人成绩和查询全班成绩。如果name未指定，则查询全班成绩。<br>
	 * 注意这里返回的是Json对象，非视图对象。
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<List<ScoreVo>> queryScore(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String namePinYin = request.getParameter(Constant.PARAM_NAME_PINYIN);
		String birthday = request.getParameter(Constant.PARAM_BIRTHDAY);
		String semesterId = request.getParameter(Constant.PARAM_SEMESTER_ID);
		String examId = request.getParameter(Constant.PARAM_EXAM_ID);
		boolean queryAll = Boolean.parseBoolean(request.getParameter(Constant.PARAM_QUERY_ALL));
		//检验验证码（测试期间注释掉）
//		String verifyCode = request.getParameter(Constant.PARAM_VERIFY_CODE);
//		String sessionVerifyCode = (String)request.getSession().getAttribute(Constant.PARAM_VERIFY_CODE);
//		if (!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
//			throw new ValidationException(ErrorEnum.ILLEGAL_REQUEST_ERROR_VERIFY_CODE);
//		}

		List<ScoreVo> scoreVos = null;
		if (StringUtils.isEmpty(examId)) {
			//如果是查询所有的历史成绩
			if (queryAll) {
				semesterId = "0"; //semesterId=0: 此条件不作为过滤条件
			}
			scoreVos = scoreService.queryScoreBySemester(namePinYin, birthday, Integer.parseInt(semesterId));
		} else {
			scoreVos = scoreService.queryScoreByClass(Integer.parseInt(examId));
		}
		
		return new JsonResponse<List<ScoreVo>>(SUCCESS, scoreVos);
	}
	
	/**
	 * 管理员修改个别成绩。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> updateScore(HttpServletRequest request, 
		HttpServletResponse response) throws BaseException, Exception {
		String scoreId = request.getParameter(Constant.PARAM_ID);				//主键ID
		String chinese = request.getParameter(Constant.PARAM_CHINESE);			//语文
		String math = request.getParameter(Constant.PARAM_MATH);				//数学
		String english = request.getParameter(Constant.PARAM_ENGLISH);			//英语
		String physics = request.getParameter(Constant.PARAM_PHYSICS);			//物理
		String chemistry = request.getParameter(Constant.PARAM_CHEMISTRY);		//化学
		String biologic = request.getParameter(Constant.PARAM_BIOLOGIC);		//生物
		String politics = request.getParameter(Constant.PARAM_POLITICS);		//政治
		String history = request.getParameter(Constant.PARAM_HISTORY);			//历史
		String geography = request.getParameter(Constant.PARAM_GEOGRAPHY);		//地理
		String physical = request.getParameter(Constant.PARAM_PHYSICAL);		//体育
		String experiment = request.getParameter(Constant.PARAM_EXPERIMENT);	//实验
		
		Score score = createScoreObject(scoreId, chinese, math, english, physics, chemistry, 
				biologic, politics, history, geography, physical, experiment);
		
		scoreService.update(score);
		return new JsonResponse<Object>(SUCCESS, null);
	}

	private Score createScoreObject(String scoreId, String chinese, String math, 
			String english, String physics, String chemistry, String biologic, 
			String politics, String history, String geography, String physical, String experiment) {
		Score score = new Score();
		score.setId(Integer.parseInt(scoreId));
		score.setChinese(chinese);
		score.setMath(math);
		score.setEnglish(english);
		score.setPhysics(physics);
		score.setChemistry(chemistry);
		score.setBiologic(biologic);
		score.setPolitics(politics);
		score.setHistory(history);
		score.setGeography(geography);
		score.setPhysical(physical);
		score.setExperiment(experiment);
		return score;
	}
}
