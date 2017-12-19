package com.my.evc.controller.springmvc;

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

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Notice;
import com.my.evc.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeService noticeService;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<String> createNotice(@RequestBody(required=true) Notice file,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		noticeService.create(null);
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView noticeDetail(@PathVariable("id") int id, 
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		Notice notice = noticeService.findByID(id);
		ModelAndView mav = new ModelAndView("notice_detail");
		mav.addObject(MODEL, notice);
		return mav;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listNotices(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		List<Notice> notices = noticeService.listNotices();
		ModelAndView mav = new ModelAndView("notice");
		mav.addObject(MODEL, notices);
		return mav;
	}
}
