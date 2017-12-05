package com.my.evc.service;

import com.my.evc.exception.BaseException;
import com.my.evc.model.User;

public interface UserService {
    public void createUser(User user) throws BaseException;
    public User getUserByID(int id) throws BaseException;
    public User login(String username, String password) throws BaseException;
}
