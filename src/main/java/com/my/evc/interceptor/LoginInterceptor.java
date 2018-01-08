package com.my.evc.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.SystemException;
import com.my.evc.model.User;
import com.my.evc.util.CommonUtil;

/**
 * 调用Restful方法之前检查登录的拦截器。
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final String ACCESS_DENY_LOG = "User not login: Resource=%s";

	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	/**
	 * 检查登录是否登录。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {

		HttpSession session = request.getSession();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String restfulServiceUri = CommonUtil.extractServiceURI(handlerMethod);
		
		try {
			User user = (User)session.getAttribute(Constant.PARAM_USER);
			if (user == null) {
				String logMessage = String.format(ACCESS_DENY_LOG, restfulServiceUri);
				logger.debug(logMessage);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.debug("Fail to check login status for resource: " + restfulServiceUri, e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws SystemException {
		try {
			super.postHandle(request, response, handler, modelAndView);
		} catch (Exception e) {
			logger.error("Login check后处理失败：" + e);
			throw new SystemException(ErrorEnum.SYSTEM_ERROR);
		}
	}
}
