package com.leyili.mapper;

import com.leyili.dto.SetmealDto;
import com.leyili.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //添加套餐菜品
    void insert(SetmealDto setmealDto);

    //实现批量删除
    void delete(@Param("ids") List<Long> ids);

    //根据菜品id获取套餐id
    List<Long> getSetmealByDish(@Param("ids") List<Long> ids);

    //根据id获取套餐信息
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getById(@Param("id") Long id);
}
