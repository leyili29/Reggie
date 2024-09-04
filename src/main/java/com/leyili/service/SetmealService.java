package com.leyili.service;

import com.leyili.dto.SetmealDto;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    //根据分类id查询套餐
    int getByCategoryId(Long ids);

    //添加套餐
    void save(SetmealDto setmealDto);

    //套餐分类查询
    PageBean page(Integer page, Integer pageSize, String name);

    //批量起售停售
    void status(Integer status, List<Long> ids, Long empId);

    //实现批量删除
    void delete(List<Long> ids);

    //根据id获取套餐信息
    SetmealDto getById(Long id);

    //修改setmeal表 套餐的基本信息
    void update(SetmealDto setmealDto);

    //根据分类id查询套餐
    List<Setmeal> list(Setmeal setmeal);
}
