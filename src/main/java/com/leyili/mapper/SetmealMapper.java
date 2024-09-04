package com.leyili.mapper;

import com.leyili.dto.SetmealDto;
import com.leyili.pojo.Setmeal;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SetmealMapper {

    //根据分类id查询套餐
    @Select("select count(*) from setmeal where category_id = #{ids}")
    int getByCategoryId(@Param("ids") Long ids);

    //添加套餐基本信息
    @Insert("insert into setmeal(id, category_id, name, price, status, code, description, image, create_time, update_time, create_user, update_user, is_deleted) " +
            "values (#{id},#{categoryId},#{name},#{price},#{status},#{code},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},#{isDeleted})")
    void insert(SetmealDto setmealDto);

    //套餐分页查询
    List<SetmealDto> page(String name);

    //批量起售停售
    void status(@Param("status") Integer status,@Param("ids") List<Long> ids,@Param("empId") Long empId,@Param("now") Date now);

    //实现批量删除
    void delete(@Param("ids") List<Long> ids);

    ///判断是否售卖,若售卖中 则抛出异常
    int checkStatus(@Param("ids") List<Long> ids);

    //根据id获取套餐信息
    @Select("select * from setmeal where id = #{id}")
    SetmealDto getById(@Param("id") Long id);

    //修改setmeal表 套餐的基本信息
    void update(SetmealDto setmealDto);

    // 根据分类id查询套餐
    @Select("select * from setmeal where category_id = #{categoryId} and status =#{status} order by update_time")
    List<Setmeal> list(Setmeal setmeal);
}
