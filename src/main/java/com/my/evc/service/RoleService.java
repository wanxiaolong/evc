package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BaseException;
import com.my.evc.mapper.RoleMapper;
import com.my.evc.model.Role;
import com.my.evc.model.User;
import com.my.evc.util.JsonUtil;

@Service
@Transactional
public class RoleService implements BaseService<Role> {

	@Autowired
	private RoleMapper roleMapper;
	
	public void create(Role role) throws BaseException {
		roleMapper.create(role);
	}
	
	public void deleteByID(int id) throws BaseException {
		roleMapper.delete(id);
	}

	public void update(Role role) throws BaseException {
		roleMapper.update(role);
	}

	public Role findByID(int id) throws BaseException {
		return roleMapper.find(id);
	}
	
	/**
	 * 获取用户的所有权限。
	 */
	public List<Integer> getPermissionsForUser(User user) throws BaseException {
		Role role = roleMapper.find(user.getRoleId());
		if (role == null) {
			throw new BaseException(ErrorEnum.INVALID_ROLE);
		}
		return JsonUtil.toList(role.getPermissions(), Integer.class);
	}
}
