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
	
	/**
	 * 用于将客户端显示的“是”或“否”转换成boolean类型的值。是=true，否=false
	 * @param s
	 * @return
	 */
	public static boolean strToBool(String s) {
		return "是".equals(s) ? true : false;
	}
}
