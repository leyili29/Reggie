package com.leyili.controller;

import com.leyili.dto.SetmealDto;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.pojo.Setmeal;
import com.leyili.service.SetmealDishService;
import com.leyili.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/*
 * 套餐管理
 * */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;


    //添加套餐
    @PostMapping
    @Transactional
    public Result save(HttpServletRequest request, @RequestBody SetmealDto setmealDto){

        log.info("添加套餐信息: {}",setmealDto);

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        setmealDto.setCreateUser(empId);
        setmealDto.setUpdateUser(empId);

        //向setmeal表里插入基本信息
         setmealService.save(setmealDto);

        //向setmeal_dish表里插入菜品信息
         setmealDishService.save(setmealDto);

        return Result.success("新增套餐成功");
    }


    //实现分页查询
    @GetMapping("/page")
    public Result page(Integer page,Integer pageSize,String name){
         log.info("套餐分页查询参数:{},{},{}",page,pageSize,name);

        PageBean pageBean = setmealService.page(page,pageSize,name);

        return Result.success(pageBean);
    }

    //批量起售停售
    @PostMapping("/status/{status}")
    public Result status(HttpServletRequest request,@PathVariable Integer status,@RequestParam List<Long> ids){

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        setmealService.status(status,ids,empId);

        return Result.success("套餐状态修改成功");
    }

    //实现批量删除
    @DeleteMapping
    @Transactional
    public Result delete(@RequestParam List<Long> ids){

        //删除setmeal表上的信息
        setmealService.delete(ids);

        //删除setmeal_dish上的信息
        setmealDishService.delete(ids);

        return Result.success("删除成功");
    }

    //根据id获取套餐信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){

        log.info("根据id查询套餐信息:{}",id);

        SetmealDto setmealDto = setmealService.getById(id);

        return Result.success(setmealDto);
    }


    //修改套餐信息
    @PutMapping
    @Transactional
    public Result update(HttpServletRequest request,@RequestBody SetmealDto setmealDto){

        log.info("更新套餐:{}",setmealDto);

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        setmealDto.setUpdateUser(empId);

        //修改setmeal表 套餐的基本信息
        setmealService.update(setmealDto);

        //根据id查出当前修改过后的套餐信息
        SetmealDto temp = setmealService.getById(setmealDto.getId());

        setmealDto.setUpdateUser(temp.getUpdateUser());
        setmealDto.setUpdateTime(temp.getUpdateTime());

        //修改setmeal_dish表 套餐的菜品信息
        setmealDishService.update(setmealDto);

        return Result.success("套餐更新成功");
    }

    /*
     * 根据分类id查询套餐
     * */
    @GetMapping("/list")
    public Result list(Setmeal setmeal){

        List<Setmeal> setmealList = setmealService.list(setmeal);

        return Result.success(setmealList);
    }

}
