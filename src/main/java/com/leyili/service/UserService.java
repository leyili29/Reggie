package com.leyili.service;

import com.leyili.pojo.User;

public interface UserService {

    //根据用户手机号来查询用户
    User getByPhone(String phone);

    //添加用户到user表
    void save(User user);

    //根据id查询用户数据
    User getById(Long userId);
}
