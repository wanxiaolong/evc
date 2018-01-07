package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.User;
import com.my.evc.security.Permission;
import com.my.evc.security.RequirePermission;
import com.my.evc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 创建用户。
	 */
	@ResponseBody
	@RequirePermission(permissions = {Permission.USER_ADD})
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<String> createUser(@RequestBody(required=true) User user,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		userService.create(user);
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
	
	/**
	 * 处理用户登录请求。
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		User user = null;
		String username = request.getParameter(Constant.PARAM_USERNAME);
		String password = request.getParameter(Constant.PARAM_PASSWORD);
		user = userService.login(username, password);
		//登录之后存储用户信息
		request.getSession().setAttribute(Constant.PARAM_USER, user);
		//更新登录日期
		userService.updateLastLogin(user.getId());
		ModelAndView mav = new ModelAndView("redirect:/home.jsp");
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
