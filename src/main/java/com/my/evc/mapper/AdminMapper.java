package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Admin;

@MapperScan
public interface AdminMapper extends BaseMapper<Admin> {
	/**
	 * 登录的时候需要更新上次登录时间
	 */
	public void updateLastLogin(int id);
}
