package com.xu.community.service;

import com.xu.community.dao.UserMapper;
import com.xu.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int userId){
        return userMapper.selectUserById(userId);
    }

}