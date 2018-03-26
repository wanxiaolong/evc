package com.my.evc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.ValidationException;
import com.my.evc.mapper.UserMapper;
import com.my.evc.model.User;
import com.my.evc.util.EncryptUtil;

@Service
@Transactional
public class UserService implements BaseService<User> {

	@Autowired
	private UserMapper userMapper;
	
	public void create(User user) throws BaseException {
		String password = user.getPassword();
		String encryptedPassword = EncryptUtil.md5(password);
		user.setPassword(encryptedPassword);
		userMapper.create(user);
	}
	
	public void deleteByID(int id) throws BaseException {
		userMapper.delete(id);
	}

	public void update(User user) throws BaseException {
		userMapper.update(user);
	}

	public User findByID(int id) throws BaseException {
		return userMapper.find(id);
	}
	
	/**
	 * 处理用户登录。
	 * @param username 用户名。
	 * @param password 用户密码（未加密的）。
	 * @return 登录成功的时候返回用户的所有信息。
	 * @throws BaseException 当用户密码错误（查找失败）的时候，抛出异常。
	 */
	public User login(String username, String password) throws BaseException {
		password = EncryptUtil.md5(password);
		User user = getUserByNameAndPass(username, password);
		if (user == null) {
			throw new ValidationException(ErrorEnum.USER_NOT_FOUND);
		}
		return user;
	}

	/**
	 * 根据用户名和密码查找用户。
	 * @param username 用户名
	 * @param encryptedPassword 用户密码（已加密）
	 */
	public User getUserByNameAndPass(String username, String encryptedPassword) throws BaseException {
		return userMapper.findByNameAndPass(username, encryptedPassword);
	}
	
	/**
	 * 更新最近登录的时间。
	 */
	public void updateLastLogin(int id) {
		userMapper.updateLastLogin(id);
	}

	/**
	 * 管理员修改密码。
	 */
	public void changePassword(User sessionUser, String oldPassword, String newPassword) throws ValidationException {
		String encryptedOldPwd = EncryptUtil.md5(oldPassword);
		if (!sessionUser.getPassword().equals(encryptedOldPwd)) {
			//旧密码不匹配
			throw new ValidationException(ErrorEnum.INVALID_PASSWORD);
		}
		//将新密码加密后放到User对象中，用来更新密码。
		sessionUser.setPassword(EncryptUtil.md5(newPassword));
		userMapper.changePassword(sessionUser);
	}
}
