package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.User;

@MapperScan
public interface UserMapper extends BaseMapper<User> {
    
}
