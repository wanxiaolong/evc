package com.my.evc.controller.springmvc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;

/**
 * 和业务没有直接关系的请求。
 */
@Controller
@RequestMapping("/server")
public class ServerController extends BaseController {
	private static final String JSON_TYPE = "application/json";

	@Autowired
	private LocaleResolver localeResolver;

	/**
	 * 自检。测试当前server是否能正常响应。
	 */
	@ResponseBody
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = JSON_TYPE)
	public JsonResponse<String> ping(String name, HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		return new JsonResponse<String>(SUCCESS, name);
	}

	/**
	 * 语言切换。
	 */
	@RequestMapping(value = "lang", method = RequestMethod.GET)
	public String language(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("lang") String language) {
		language = language.toLowerCase();
		if ("en".equalsIgnoreCase(language)) {
			localeResolver.setLocale(request, response, Locale.US);
		} else {
			localeResolver.setLocale(request, response, Locale.CHINA);
		}
		return "index";
	}
}
