package com.leyili.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyili.mapper.EmployeeMapper;
import com.leyili.pojo.Employee;
import com.leyili.pojo.PageBean;
import com.leyili.service.EmployeeService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(Employee employee) {

        return employeeMapper.getByUsername(employee);

    }

    @Override
    public void save(Employee employee) {
        Date now = new Date();
        employee.setCreateTime(now);
        employee.setUpdateTime(now);

        //设置初始密码,需要进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //根据username生成id
        employee.setId(GenerateID.generateEmployeeID(employee.getUsername()));

        employeeMapper.insert(employee);
    }

    //实现分页查询
    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
         //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询
        List<Employee> employeeList =employeeMapper.list(name);
        Page<Employee> p = (Page<Employee>) employeeList;

        return new PageBean(p.getTotal(),p.getResult());
    }

    //根据id更新员工信息
    @Override
    public void updateByid(Employee employee) {
        Date now = new Date();
        employee.setUpdateTime(now);

        employeeMapper.updateById(employee);
    }

    //根据id查询员工信息
    @Override
    public Employee getById(Long id) {

        return employeeMapper.getById(id);
    }
}
