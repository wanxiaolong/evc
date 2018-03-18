package com.my.evc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.exception.BaseException;
import com.my.evc.model.User;
import com.my.evc.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 处理用户登录请求。
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		String username = request.getParameter(Constant.PARAM_USERNAME);
		String password = request.getParameter(Constant.PARAM_PASSWORD);
		String ru = request.getParameter(Constant.PARAM_RU);
		User user = userService.login(username, password);
		//登录之后存储用户信息
		request.getSession().setAttribute(Constant.PARAM_USER, user);
		//更新登录日期
		userService.updateLastLogin(user.getId());
		
		String target = (ru != null) ? ru : "/admin/home.jsp";
		ModelAndView mav = new ModelAndView("redirect:" + target);
		mav.addObject(MODEL, user.getUsername());
		return mav;
	}
	
	/**
	 * 登出。
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		request.getSession().removeAttribute(Constant.PARAM_USER);
		ModelAndView mav = new ModelAndView("redirect:/home.jsp");
		return mav;
	}
}
