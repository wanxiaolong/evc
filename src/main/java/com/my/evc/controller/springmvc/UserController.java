package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.User;
import com.my.evc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private static final String SESSION_NAME = "user";
	
	@Autowired
	private UserService userService;
	
	private final Logger LOGGER = Logger.getLogger(UserController.class);

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public JsonResponse<String> createUser(@RequestBody(required=true) User user,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		try {
			userService.create(user);
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		User user = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			user = userService.login(username, password);
			//登录之后存储用户信息
			request.getSession().setAttribute("user", user);
			//更新登录日期
			userService.updateLastLogin(user.getId());
		} catch (BaseException e) {
			LOGGER.error(e.getErrorCode() + e.getErrorMessage());
			throw new BaseException();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		ModelAndView mav = new ModelAndView("redirect:/home.jsp");
		mav.addObject("username", user.getUsername());
		return mav;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		try {
			request.getSession().removeAttribute("user");
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception();
		}
		ModelAndView mav = new ModelAndView("redirect:/home.jsp");
		return mav;
	}
	
	/**
	 * 检查用户是否已经登录。
	 */
	public boolean hasLogin(HttpSession session) {
		Object object = session.getAttribute(SESSION_NAME);
		return (object == null) ? true : false;
	}
}
