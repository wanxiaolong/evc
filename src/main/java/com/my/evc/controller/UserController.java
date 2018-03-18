package com.my.evc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public JsonResponse<String> createUser(@RequestBody(required=true) User user,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		userService.create(user);
		return new JsonResponse<String>(SUCCESS, "Created succeed!");
	}
}
