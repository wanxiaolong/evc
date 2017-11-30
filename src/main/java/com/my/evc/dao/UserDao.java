package com.my.evc.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.User;

@MapperScan
public interface UserDao extends BaseDao<User> {
    
}
