package com.my.evc.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.SystemException;
import com.my.evc.model.User;
import com.my.evc.util.CommonUtil;
import com.my.evc.util.StringUtil;

/**
 * 检查是否登录的拦截器。
 * 注意：拦截器拦截的是Controller中的请求，而不是静态资源。<br>
 * 比如：<br>
 * <li>/admin/path会被拦截（因为该请求由Controller返回），</li>
 * <li>/admin/path.jsp则不会被拦截（因为该请求没有经过Controller，当然也可以为该请求配置Controller，但我没这么做）。</li>
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	//这是拦截的白名单，在这里面的请求则不会被拦截。
	//这些请求也可以再spring-context.xml中以<mvc:exclude-mapping path=""/>的方式指定
	private List<String> excludedUrls = null;
	
	/**
	 * 检查用户是否登录。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		String ctxPath = request.getContextPath();
		String queryString = request.getQueryString();
		//targetURL: 除掉项目名称后访问的路径
		String targetURL = request.getRequestURI().substring(ctxPath.length());

		//如果访问的是白名单，则不进行拦截。
		if (excludedUrls != null && excludedUrls.size() > 0) {
			for(String excludeUrl : excludedUrls) {
				if (excludeUrl.contains(targetURL)) {
					return true;
				}
			}
		}
		
		//如果用户没有登录，跳转到登录页，并且带上当前URI，便于登陆之后跳转回来
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.PARAM_USER);
			if (user == null) {
				String contextPath = request.getContextPath();
				String redirectURL = contextPath + "/admin/login.jsp?ru=" + targetURL;
				if (!StringUtil.isEmpty(queryString)) {
					redirectURL += queryString;
				}
				response.sendRedirect(redirectURL);
				return false;
			}
			return true;
		} catch (Exception e) {
			String uri = CommonUtil.extractRequestURI(request);
			log.debug("Fail to check login status for resource: " + uri, e);
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
			log.error("Login check后处理失败：" + e);
			throw new SystemException(ErrorEnum.SYSTEM_ERROR);
		}
	}
	
	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}
}