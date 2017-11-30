package com.my.evc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.UserMapper;
import com.my.evc.model.User;
import com.my.evc.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    public void createUser(User user) throws BaseException {
        userMapper.create(user);
    }

    public User findUserByID(int id) throws BaseException {
        return userMapper.find(id);
    }

    public void deleteUserByID(int id) throws BaseException {
        userMapper.delete(id);
    }

    public void updateUser(User user) throws BaseException {
        userMapper.update(user);
    }

}
