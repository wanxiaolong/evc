package com.my.evc.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.User;

@MapperScan
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 使用@Param注解，可以在*Mapper.xml中使用parameterType="map"
	 */
	public User findByNameAndPass(
			@Param(value = "username") String username, 
			@Param(value = "password") String password);
	
	/**
	 * 登录的时候需要更新上次登录时间
	 */
	public void updateLastLogin(int id);
}
