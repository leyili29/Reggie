package com.leyili.service.impl;

import com.leyili.mapper.AddressBookMapper;
import com.leyili.pojo.AddressBook;
import com.leyili.service.AddressBookService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /*
    *新增地址信息
    * */
    @Override
    public void save(AddressBook addressBook) {
        Date now = new Date();
        addressBook.setCreateTime(now);
        addressBook.setUpdateTime(now);

        //生成地址信息的id
        addressBook.setId(GenerateID.generateEmployeeID(addressBook.getUserId()+addressBook.getConsignee()+addressBook.getPhone()));

        addressBookMapper.insert(addressBook);
    }

    //根据userId将该用户下的所有地址信息全部设置为0
    @Override
    public void update(Long userId) {
         addressBookMapper.update(userId);
    }

    //将需要的地址信息设置为默认地址
    @Override
    public void updateById(AddressBook addressBook) {
         addressBookMapper.updateById(addressBook);
    }

    // 根据id查询地址
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    //查询默认地址
    @Override
    public AddressBook getOne(Long userId) {
        return addressBookMapper.getOne(userId);
    }

    //查询指定用户的全部地址
    @Override
    public List<AddressBook> list(Long userId) {
        return addressBookMapper.list(userId);
    }

    //修改地址信息
    @Override
    public void alter(AddressBook addressBook) {
        Date now = new Date();
        addressBook.setUpdateTime(now);
        addressBookMapper.alter(addressBook);
    }

    //删除地址
    @Override
    public void delete(Long ids) {
        addressBookMapper.deleteById(ids);
    }

}
