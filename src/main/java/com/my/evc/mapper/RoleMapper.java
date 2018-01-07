package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Role;

@MapperScan
public interface RoleMapper extends BaseMapper<Role> {
	
}
