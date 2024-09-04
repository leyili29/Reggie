package com.leyili.mapper;

import com.leyili.dto.DishDto;
import com.leyili.pojo.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface DishMapper {

    //根据分类id查询菜品
    @Select("select count(*) from dish where category_id = #{ids}")
    int getByCategoryId(@Param("ids") Long ids);

    //添加菜品
    @Insert("insert into dish(id, name, category_id, price, code, image, description, status, sort, create_time, update_time, create_user, update_user, is_deleted)" +
            "values (#{id},#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},#{isDeleted})")
    void insert(DishDto dishDto);

    //分页查询
    List<DishDto> list(String name);

    //根据id查询菜品信息
    @Select("select * from dish where id = #{id}")
    DishDto getById(@Param("id") Long id);

    //修改菜品
    void update(DishDto dishDto);

    //批量起售停售
    void status(@Param("status") Integer status,@Param("ids") List<Long> ids,@Param("empId") Long empId,@Param("now") Date now);

    //实现批量删除
    void delete(@Param("ids")List<Long> ids);

    //根据分类查询菜品
    @Select("select * from dish where category_id = #{categoryId} and status = 1 order by sort,update_time DESC")
    List<DishDto> listByCategory(Dish dish);
}
