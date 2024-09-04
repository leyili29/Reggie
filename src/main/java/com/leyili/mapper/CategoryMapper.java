package com.leyili.mapper;

import com.leyili.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增分类
    @Insert("insert into category(id, type, name, sort, create_time, update_time, create_user, update_user)  " +
            "values (#{id},#{type},#{name},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

    //查询所有信息
    @Select("select * from category order by sort")
    List<Category> list();

    //根据id删除分类
    @Delete("delete from category where id = #{ids}")
    void deleteById(@Param("ids") Long ids);

    //修改分类信息
    @Update("update category set name = #{name},sort=#{sort},update_time = #{updateTime},update_user = #{updateUser} where id = #{id}")
    void update(Category category);

    //根据类型查询
    List<Category> getByType(Category category);

    //根据id查询类型名称
    @Select("select name from category where id =#{categoryId}")
    String getNameById(Long categoryId);
}
