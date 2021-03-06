package com.my.evc.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.evc.common.Constant;
import com.my.evc.util.StringUtil;

/**
 * 进入管理页面之前检查登录的拦截器。
 * 注意，该过滤器仅用于过滤直接访问以*.jsp规则的请求。详见web.xml中Filter的声明。
 */
public class LoginFilter implements Filter {
	private static final String LOGIN_URL = "/admin/login.jsp";
	
	/**
	 * 过滤请求。如果用户访问的是需要登录的页面（在admin目录下）并且当前没有登录过，则直接跳转到/admin/login.jsp
	 */
	@SuppressWarnings("deprecation")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String ctxPath = request.getContextPath();
		String queryString = request.getQueryString();
		//targetURL: 除掉项目名称后访问的路径
		String targetURL = request.getRequestURI().substring(ctxPath.length());
		HttpSession session = request.getSession(false);
		
		//如果打开的是login页面而且当前已经登录，则跳转到管理员的主页面/admin/home.jsp
		if (LOGIN_URL.equals(targetURL)) {
			if(session != null && session.getAttribute(Constant.PARAM_USER) != null){
				response.sendRedirect(ctxPath + "/admin/home.jsp");
				return;
			}
		}
		
		//对当前页面进行判断，如果当前页面不为登录页面
		if(targetURL.startsWith("/admin/") && !LOGIN_URL.equals(targetURL)){
			//在不为登陆页面时，再进行判断，如果不是登陆页面也没有session则跳转到登录页面，
			if(session == null || session.getAttribute(Constant.PARAM_USER) == null){
				String redirectURL = ctxPath + "/admin/login.jsp?ru=" + targetURL;
				if (!StringUtil.isEmpty(queryString)) {
					redirectURL += URLEncoder.encode("?" + queryString);
				}
				response.sendRedirect(redirectURL);
				return;
			}
		}
		//这里表示如果当前页面是登陆页面，跳转到登陆页面
		filterChain.doFilter(request, response);
		return;
	}
	
	public void init(FilterConfig filterConfig)throws ServletException{
		
	}

	public void destroy() {
		
	}
}
