package com.leyili.service;

import com.leyili.pojo.AddressBook;

import java.util.List;

public interface AddressBookService {

    //新增地址信息
    void save(AddressBook addressBook);

    //根据userId将该用户下的所有地址信息全部设置为0
    void update(Long userId);

    //将需要的地址信息设置为默认地址
    void updateById(AddressBook addressBook);

    // 根据id查询地址
    AddressBook getById(Long id);

    //查询默认地址
    AddressBook getOne(Long userId);

    //查询指定用户的全部地址
    List<AddressBook> list(Long userId);

    //修改地址信息
    void alter(AddressBook addressBook);

    //删除地址
    void delete(Long ids);
}
