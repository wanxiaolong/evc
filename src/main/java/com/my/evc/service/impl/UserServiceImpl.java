package com.my.evc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.dao.UserDao;
import com.my.evc.exception.BaseException;
import com.my.evc.model.User;
import com.my.evc.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    public void createUser(User user) throws BaseException {
        userDao.create(user);
    }

    public User getUserByID() throws BaseException {
        return null;
    }
}
