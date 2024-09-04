package com.leyili.service.impl;

import com.leyili.dto.DishDto;
import com.leyili.mapper.DishFlavorMapper;
import com.leyili.pojo.DishFlavor;
import com.leyili.service.DishFlavorService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    //添加菜品
    @Override
    public void save(DishDto dishDto) {
        //菜品ID
        Long dishId=dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setId(GenerateID.generateEmployeeID(dishId+item.getName()));
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishDto.setFlavors(flavors);

        dishFlavorMapper.insert(dishDto);

    }


    //更新口味表(先删除后添加)
    @Override
    public void update(DishDto dishDto) {
         //先删除表当中的口味信息
         dishFlavorMapper.delete(dishDto.getId());

         //插入信息
         save(dishDto);
    }

    //实现批量删除
    @Override
    public void delete(List<Long> ids) {
         dishFlavorMapper.BatchDelete(ids);
    }

}
