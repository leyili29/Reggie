package com.leyili.service;

import com.leyili.dto.DishDto;
import com.leyili.pojo.Dish;
import com.leyili.pojo.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DishService {

    //根据分类id查询菜品
    int getByCategoryId(Long ids);

    //添加菜品
    void save(DishDto dishDto);

    //实现分页查询
    PageBean page(Integer page, Integer pageSize, String name);

    //根据id查询菜品信息以及口味
    DishDto getByIdWithFlavor(Long id);

    //修改菜品
    void update(DishDto dishDto);

    DishDto getById(Long id);

    //批量起售停售
    void status(HttpServletRequest request,Integer status, List<Long> ids, Long empId);

    //实现批量删除
    void delete(List<Long> ids);

    //根据分类查询菜品
    List<DishDto> list(Dish dish);
}
