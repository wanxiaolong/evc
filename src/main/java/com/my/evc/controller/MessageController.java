package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Message;
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
	@RequirePermission(permissions = {Permission.MESSAGE_ADD})
	@RequestMapping(value="create", method = RequestMethod.POST)
	public ModelAndView createMessage(
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		String type = request.getParameter(Constant.PARAM_TYPE);
		String title = request.getParameter(Constant.PARAM_TITLE);
		String contact = request.getParameter(Constant.PARAM_CONTACT);
		String content = request.getParameter(Constant.PARAM_CONTENT);
		
		Message message = new Message(type,title, contact, content);
		
		messageService.create(message);
		return getListView();
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
		return getListView();
	}
	
	private ModelAndView getListView() throws BaseException {
		List<Message> messages = messageService.findAll();
		ModelAndView mav = new ModelAndView("message");
		mav.addObject(MODEL, messages);
		return mav;
	}
}
