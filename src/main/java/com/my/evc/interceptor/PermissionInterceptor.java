package com.my.evc.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BusinessException;
import com.my.evc.exception.SystemException;
import com.my.evc.model.User;
import com.my.evc.security.RequirePermission;
import com.my.evc.service.RoleService;
import com.my.evc.util.CommonUtil;

/**
 * 调用Restful方法之前检查权限的拦截器。
 */
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private static final String ACCESS_DENY_LOG = "Access denied: User=%s, Role=%s, Resource=%s";

	@Autowired
	private RoleService roleService;
	
	/**
	 * 检查登录用户的权限。
	 * 如果用户有该权限，则返回true，否则返回false并且记录拒绝访问的日志。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {

		HttpSession session = request.getSession();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		try {
			//利用反射获取方法需要的权限
			RequirePermission requirePermission = handlerMethod.getMethodAnnotation(RequirePermission.class);
			if (requirePermission == null) {
				//对于没有使用RequirePermission注解来声明权限的方法，直接跳过权限验证。即默认是有权限的。
				return true;
			}
			int[] requiredPermissions = requirePermission.permissions();
			//根据用户的角色，查询数据库中的权限
			List<Integer> userPermissions = getCurrentUserPermissions(session);
			//权限检查
			boolean hasPermission = containsAny(userPermissions, requiredPermissions);
			if (!hasPermission) {
				User user = (User)session.getAttribute(Constant.PARAM_USER);
				String logMessage = String.format(ACCESS_DENY_LOG,user.getUsername(), user.getRoleId(), CommonUtil.extractRequestURI(request));
				log.debug(logMessage);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
			return true;
		} catch (Exception e) {
			log.debug("Fail to authorize RESTful service call", e);
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
			log.error("Restful service权限后处理失败：" + e);
			throw new SystemException(ErrorEnum.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 检查用户是否有需要的权限。
	 *
	 * @param userPermissions 当前用户的所有权限列表
	 * @param requiredPermissions 需要的权限列表
	 * @return true 如果用户至少有任何一个需要的权限，返回true，否则返回false
	 */
	private Boolean containsAny(List<Integer> userPermissions, int[] requiredPermissions) {
		if (userPermissions != null && userPermissions.size() > 0) {
			for (Integer requiredPermission : requiredPermissions) {
				if (userPermissions.contains(requiredPermission)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前用户的所有权限。
	 */
	private List<Integer> getCurrentUserPermissions(HttpSession session) throws BusinessException {
		//先从session里面取，提高响应速度
		@SuppressWarnings("unchecked")
		List<Integer> permissions = (List<Integer>) session.getAttribute(Constant.PARAM_PERMISSIONS);
		
		//如果session中没有，则从数据库里取
		if (permissions == null) {
			try {
				User user = (User)session.getAttribute(Constant.PARAM_USER);
				permissions = roleService.getPermissionsForUser(user);
				//取到后，放入session中，便于下次读取
				session.setAttribute(Constant.PARAM_PERMISSIONS, permissions);
			} catch (Exception e) {
				log.error("获取用户权限失败：" + e);
				throw new BusinessException(ErrorEnum.SYSTEM_ERROR);
			}
		}
		return permissions;
	}
}
