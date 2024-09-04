package com.leyili.controller;


import com.leyili.pojo.Employee;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody Employee employee){

     // 1 将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

     //2 根据用户名查询数据库
        Employee e = employeeService.login(employee);

     //3 如果没有查询到 返回登录失败
         if(e==null){
             return Result.error("登录失败");
         }

     //4 密码比对
          if(!e.getPassword().equals(password)) {
              return Result.error("登录失败");
          }

     //5 查询员工状态 如禁用 则返回禁用信息
         if(e.getStatus() == 0){
             return Result.error("账号已禁用");
         }
     //6 登录成功  将员工id存入Session并返回登录成功结果
       request.getSession().setAttribute("employee",e.getId());log.info("查询员工:{}",e);
        return Result.success(e);
    }


    //退出
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        //清理session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");

        return Result.success("退出成功");
    }

    //新增员工
     @PostMapping
     public Result save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工:{}",employee);

         //获得当前登录用户的id
         Long empId = (Long)request.getSession().getAttribute("employee");
         employee.setCreateUser(empId);
         employee.setUpdateUser(empId);

         //调用service新增员工
         employeeService.save(employee);

         return Result.success("新增员工成功");
     }

     //实现分页查询
    @GetMapping("/page")
    public Result page(Integer page, Integer pageSize,String name){
        log.info("分页查询,参数{},{},{}",page,pageSize,name);

        PageBean pageBean = employeeService.page(page,pageSize,name);

        return Result.success(pageBean);
    }

    /*
    * 根据id修改员工信息
    * */
    @PutMapping
    public Result update(HttpServletRequest request,@RequestBody Employee employee){
         log.info("根据id修改员工信息");

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setUpdateUser(empId);

         employeeService.updateByid(employee);

        return Result.success("员工信息修改成功");
    }

    //编辑员工信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        log.info("根据id查询员工信息: {}",id);

        Employee employee =  employeeService.getById(id);

        if(employee!=null){
            return Result.success(employee);
        }

        return Result.error("没有查询到对应员工信息");
    }
}
