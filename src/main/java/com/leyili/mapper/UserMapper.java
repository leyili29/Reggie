package com.leyili.mapper;


import com.leyili.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    //根据用户手机号来查询用户
    @Select("select * from [user] where phone = #{phone}")
    User getByPhone(@Param("phone") String phone);

    //新增用户
    @Insert("insert into [user](id, name, phone, sex, id_number, avatar, status) " +
            "values (#{id},#{name},#{phone},#{sex},#{idNumber},#{avatar},#{status})")
    void insert(User user);

    //根据id查询用户信息
    @Select("select * from [user] where id = #{userId}")
    User getById(@Param("userId") Long userId);
}
