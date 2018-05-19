package com.my.evc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.ValidationException;
import com.my.evc.model.User;
import com.my.evc.service.UserService;
import com.my.evc.util.StringUtil;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	private static final String ADMIN_HOME = "/admin/home.jsp";
	private static final String HOME = "/home.jsp";
	
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
		//默认跳转到admin/home.jsp，如果ru不为空，则跳转到ru
		String target = (StringUtil.isEmpty(ru)) ? ADMIN_HOME : ru;
		ModelAndView mav = new ModelAndView("redirect:" + target);
		return mav;
	}
	
	/**
	 * 退出登录，之后直接回到系统的首页。
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		request.getSession().removeAttribute(Constant.PARAM_USER);
		ModelAndView mav = new ModelAndView("redirect:" + HOME);
		return mav;
	}

	/**
	 * 修改密码，之后直接回到系统的首页。
	 */
	@RequestMapping(value="/change_pwd", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> changePassword(HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		User user = (User)request.getSession().getAttribute(Constant.PARAM_USER);
		String oldPassword = request.getParameter(Constant.PARAM_PASSWORD);
		String newPassword = request.getParameter(Constant.PARAM_PASSWORD_NEW);
		String newPasswordConfirm = request.getParameter(Constant.PARAM_PASSWORD_CONFIRM);
		String verifyCode = request.getParameter(Constant.PARAM_VERIFY_CODE);
		
		String sessionVerifyCode = (String)request.getSession().getAttribute(Constant.PARAM_VERIFY_CODE);
		if (!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
			//验证码不通过
			throw new ValidationException(ErrorEnum.INVALID_VERIFY_CODE);
		}
		if (!newPassword.equals(newPasswordConfirm)) {
			//确认密码不通过
			throw new ValidationException(ErrorEnum.INVALID_PASSWORD_CONFIRM);
		}
		
		userService.changePassword(user, oldPassword, newPassword);
		return new JsonResponse<Object>(SUCCESS);
	}
}
