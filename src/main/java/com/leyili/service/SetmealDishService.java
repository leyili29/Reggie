package com.leyili.service;

import com.leyili.dto.SetmealDto;

import java.util.List;

public interface SetmealDishService {

    //添加套餐菜品
    void save(SetmealDto setmealDto);

    //实现批量删除
    void delete(List<Long> ids);

    //修改setmeal_dish表 套餐的菜品信息
    void update(SetmealDto setmealDto);
}
