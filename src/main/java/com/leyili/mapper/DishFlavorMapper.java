package com.leyili.mapper;

import com.leyili.dto.DishDto;
import com.leyili.pojo.DishFlavor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insert(DishDto dishDto);

    //根据id查询菜品的口味
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getById(@Param("id") Long id);

    //根据菜品id删除菜品口味
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void delete(@Param("id") Long id);

    //实现批量删除
    void BatchDelete(@Param("ids") List<Long> ids);
}
