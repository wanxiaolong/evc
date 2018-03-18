package com.my.evc.util;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	/**
	 * 用于拦截器调用的：获取访问的Service的URI。例子：<br>
	 * <li>POST:/file/upload</li>
	 * <li>GET:/file/list</li>
	 */
	public static String extractRequestURI(HttpServletRequest request) {
		String method = request.getMethod();
		String uri = request.getRequestURI();
		return method + ":" + uri;
	}
}
