package com.my.evc.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.SystemException;
import com.my.evc.model.User;
import com.my.evc.util.CommonUtil;

/**
 * 检查是否登录的拦截器。
 * 注意：拦截器拦截的是Controller中的请求，而不是静态资源。
 * 比如：
 * /admin/path会被拦截（因为该请求由Controller返回），
 * /admin/path.jsp则不会被拦截（因为该请求没有经过Controller，当然也可以为该请求配置Controller，但我没这么做）。
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	//这是拦截的白名单，在这里面的请求则不会被拦截。
	//这些请求也可以再spring-context.xml中以<mvc:exclude-mapping path=""/>的方式指定
	private List<String> excludedUrls = null;
	
	/**
	 * 检查用户是否登录。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		//如果访问的是白名单，则不进行拦截。
		if (excludedUrls != null && excludedUrls.size() > 0) {
			for(String excludeUrl : excludedUrls) {
				String ctxPath = request.getContextPath();
				//targetURL: 除掉项目名称后访问的路径
				String targetURL = request.getRequestURI().substring(ctxPath.length());
				if (excludeUrl.contains(targetURL)) {
					return true;
				}
			}
		}
		
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.PARAM_USER);
			//如果用户没有登录，跳转到登录页，并且带上当前URI，便于登陆之后跳转回来
			if (user == null) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/admin/login.jsp");
				return false;
			}
			return true;
		} catch (Exception e) {
			String uri = CommonUtil.extractRequestURI(request);
			logger.debug("Fail to check login status for resource: " + uri, e);
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
	
	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}
}