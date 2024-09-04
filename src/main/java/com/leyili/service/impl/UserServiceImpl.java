package com.leyili.service.impl;

import com.leyili.mapper.UserMapper;
import com.leyili.pojo.User;
import com.leyili.service.UserService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    //添加用户到user
    @Override
    public void save(User user) {
         //生成用户的id
         user.setId(GenerateID.generateEmployeeID(user.getPhone()));
         userMapper.insert(user);
    }


    @Override
    public User getById(Long userId) {
       return userMapper.getById(userId);
    }
}
