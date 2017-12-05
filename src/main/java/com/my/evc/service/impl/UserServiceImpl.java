package com.my.evc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.common.ErrorCode;
import com.my.evc.exception.BaseException;
import com.my.evc.exception.BusinessException;
import com.my.evc.mapper.UserMapper;
import com.my.evc.model.User;
import com.my.evc.service.UserService;
import com.my.evc.util.EncryptionUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    
    @Autowired
    private UserMapper userMapper;
    
    public void createUser(User user) throws BaseException {
        String password = user.getPassword();
        String encryptedPassword = EncryptionUtil.md5(password);
        user.setPassword(encryptedPassword);
        userMapper.create(user);
    }
    
    public User login(String username, String password) throws BaseException {
        password = EncryptionUtil.md5(password);
        User user = getUserByNameAndPass(username, password);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "User not found! username=" + username);
        }
        return user;
    }

    public User getUserByID(int id) throws BaseException {
        return userMapper.find(id);
    }
    
    public User getUserByNameAndPass(String username, String encryptedPassword) throws BaseException {
        return userMapper.findByNameAndPass(username, encryptedPassword);
    }
}
