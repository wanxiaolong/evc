package com.my.evc.service;

import com.my.evc.exception.BaseException;
import com.my.evc.model.User;

public interface UserService {
    public void createUser(User user) throws BaseException;
    public void deleteUserByID(int id) throws BaseException;
    public void updateUser(User user) throws BaseException;
    public User findUserByID(int id) throws BaseException;
}
