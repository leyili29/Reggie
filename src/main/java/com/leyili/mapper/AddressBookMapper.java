package com.leyili.mapper;

import com.leyili.pojo.AddressBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    //新增地址信息
    @Insert("insert into address_book(id, user_id, consignee, sex, phone,detail,label,create_time, update_time, create_user, update_user) " +
            "values (#{id},#{userId},#{consignee},#{sex},#{phone},#{detail},#{label},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(AddressBook addressBook);

    //根据userId将该用户下的所有地址信息全部设置为0
    @Update("update address_book set is_default = 0 where user_id = #{userId}")
    void update(@Param("userId") Long userId);

    //将需要的地址信息设置为默认地址
    @Update("update address_book set is_default = #{isDefault} where id = #{id}")
    void updateById(AddressBook addressBook);

    // 根据id查询地址
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(@Param("id") Long id);

    //查询默认地址
    @Select("select * from address_book where user_id = #{userId} and is_default = 1")
    AddressBook getOne(Long userId);

    //查询指定用户的全部地址
    @Select("select * from address_book where user_id = #{userId} order by update_time desc")
    List<AddressBook> list(@Param("userId") Long userId);

    //修改地址信息
    @Update("update address_book set consignee = #{consignee},sex = #{sex},phone=#{phone}," +
            "detail=#{detail},label=#{label},update_time = #{updateTime} where id = #{id}")
    void alter(AddressBook addressBook);

    //删除地址
    @Delete("delete from address_book where id = #{ids}")
    void deleteById(@Param("ids") Long ids);
}
