package com.lwq.miaosha06.service;

import com.lwq.miaosha06.dao.UserDao;
import com.lwq.miaosha06.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 23:17
 * @Version 1.0
 * @Describe
 */
@SuppressWarnings("all")
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

}
