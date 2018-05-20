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
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Notice;
import com.my.evc.model.User;
import com.my.evc.security.Permission;
import com.my.evc.security.RequirePermission;
import com.my.evc.service.NoticeService;

/**
 * 本类处理公告相关的请求。
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 创建公告。
	 */
	@ResponseBody
	@RequirePermission(permissions = {Permission.NOTICE_ADD})
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<String> createNotice(@RequestBody(required=true) Notice file,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		noticeService.create(null);
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
	
	/**
	 * 公告详情。
	 */
	@RequestMapping(value="/view/{id}")
	public ModelAndView noticeDetail(@PathVariable("id") int id, 
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		Notice notice = noticeService.findByID(id);
		ModelAndView mav = new ModelAndView("notice_detail");
		mav.addObject(MODEL, notice);
		return mav;
	}
	
	/**
	 * 更新公告。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<String> update(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		User loginUser = (User)request.getSession().getAttribute(Constant.PARAM_USER);
		
		String id = request.getParameter(Constant.PARAM_ID);
		String title = request.getParameter(Constant.PARAM_TITLE);
		String importantLevel = request.getParameter(Constant.PARAM_IMPORTANT_LEVEL);
		String content = request.getParameter(Constant.PARAM_CONTENT);
		Notice notice = new Notice();
		notice.setId(Integer.parseInt(id));
		notice.setImportantLevel(importantLevel);
		notice.setContent(content);
		notice.setTitle(title);
		//这里用当前登录的用户更新公告
		notice.setUserId(String.valueOf(loginUser.getId()));
		
		noticeService.update(notice);
		return new JsonResponse<String>(SUCCESS, "Update succeed!");
	}

	/**
	 * 删除公告。
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> deleteById(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		//从数据库中删除记录
		noticeService.deleteByID(Integer.parseInt(id));
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 公告列表。
	 */
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Notice>> listNotices(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		List<Notice> notices = noticeService.findAll();
		return new JsonResponse<List<Notice>>(SUCCESS, notices);
	}
}
