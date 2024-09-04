package com.leyili.service.impl;

import com.leyili.dto.SetmealDto;
import com.leyili.mapper.SetmealDishMapper;
import com.leyili.pojo.SetmealDish;
import com.leyili.service.SetmealDishService;
import com.leyili.utils.GenerateID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //添加套餐菜品
    @Override
    public void save(SetmealDto setmealDto) {
         //套餐id
         Long setmealId = setmealDto.getId();

        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();

        //遍历集合
        setmealDishList = setmealDishList.stream().map((item)->{
            item.setId(GenerateID.generateEmployeeID(setmealId+item.getName()));
            item.setSetmealId(setmealId);
            item.setSort(0);
            return item;
        }).collect(Collectors.toList());

       setmealDto.setSetmealDishes(setmealDishList);

       setmealDishMapper.insert(setmealDto);

    }

    //实现批量删除
    @Override
    public void delete(List<Long> ids) {
        setmealDishMapper.delete(ids);
    }

    //修改setmeal_dish表 套餐的菜品信息
    @Override
    public void update(SetmealDto setmealDto) {

        //先删除setmeal_dish表中的信息
        List<Long> ids = new ArrayList<>();
        ids.add(setmealDto.getId());
        setmealDishMapper.delete(ids);

        //再将修改后的菜品插入
        save(setmealDto);

    }


}
