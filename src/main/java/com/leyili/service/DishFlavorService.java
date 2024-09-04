package com.leyili.service;

import com.leyili.dto.DishDto;

import java.util.List;

public interface DishFlavorService {
    //添加菜品口味
    void save(DishDto dishDto);

    //更新口味表(先删除后添加)
    void update(DishDto dishDto);

    //实现批量删除
    void delete(List<Long> ids);
}
