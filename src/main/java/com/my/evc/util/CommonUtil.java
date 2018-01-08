package com.my.evc.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

public class CommonUtil {
	/**
	 * 用于拦截器调用的：获取访问的Service的URI。例子：<br>
	 * <li>POST:/file/upload</li>
	 * <li>GET:/file/list</li>
	 */
	public static String extractServiceURI(HandlerMethod handlerMethod) {
		//获取标注在Class级别的RequestMapping的value
		String classUri = handlerMethod.getBean().getClass().getAnnotation(RequestMapping.class).value()[0];
		
		//获取标注在Method级别的RequestMapping的value
		String methodUri = handlerMethod.getMethodAnnotation(RequestMapping.class).value()[0];
		
		//获取标注在Method级别的RequestMapping的method
		String requestType = handlerMethod.getMethodAnnotation(RequestMapping.class).method()[0].toString();
		return requestType + ":" + classUri + methodUri;
	}
}
