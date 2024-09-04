package com.leyili.service;

import com.leyili.pojo.Employee;
import com.leyili.pojo.PageBean;

public interface EmployeeService {

    //员工登录操作
    Employee login(Employee employee);

    //新增员工操作
    void save(Employee employee);

    //实现分页查询
    PageBean page(Integer page, Integer pageSize, String name);

    //根据id更新员工信息
    void updateByid(Employee employee);

    //根据id查询员工信息
    Employee getById(Long id);
}
