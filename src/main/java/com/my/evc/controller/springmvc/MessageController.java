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
import com.my.evc.model.Message;
import com.my.evc.model.Notice;
import com.my.evc.security.Permission;
import com.my.evc.security.RequirePermission;
import com.my.evc.service.MessageService;

/**
 * 本类处理公告相关的请求。
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 创建留言。
	 */
	@ResponseBody
	@RequirePermission(permissions = {Permission.MESSAGE_ADD})
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<String> createMessage(@RequestBody(required=true) Notice file,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		messageService.create(null);
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
	
	/**
	 * 留言详情。
	 */
	@RequestMapping(value="/{id}")
	public ModelAndView messageDetail(@PathVariable("id") int id, 
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		Message message = messageService.findByID(id);
		ModelAndView mav = new ModelAndView("message_detail");
		mav.addObject(MODEL, message);
		return mav;
	}
	
	/**
	 * 留言列表。
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listNotices(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		List<Message> messages = messageService.listNotices();
		ModelAndView mav = new ModelAndView("message");
		mav.addObject(MODEL, messages);
		return mav;
	}
}
