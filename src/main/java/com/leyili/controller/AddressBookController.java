package com.leyili.controller;

import com.leyili.pojo.AddressBook;
import com.leyili.pojo.Result;
import com.leyili.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /*
    * 新增
    * */
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook, HttpServletRequest request){
        //获取当前登录的用户id
        Long userId = (Long) request.getSession().getAttribute("user");
        addressBook.setUserId(userId);
        addressBook.setUpdateUser(userId);
        addressBook.setCreateUser(userId);

        log.info("新增地址 addressBook:{}", addressBook);
        addressBookService.save(addressBook);

        return Result.success(addressBook);
    }


    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public Result setDefault(@RequestBody AddressBook addressBook, HttpServletRequest request) {
        log.info("addressBook:{}", addressBook);

        //获取当前登录的用户id
        Long userId = (Long) request.getSession().getAttribute("user");

        //根据userId将该用户下的所有地址信息全部设置为0
          addressBookService.update(userId);

        //将需要的地址信息设置为默认地址
        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(addressBook);
        } else {
            return Result.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public Result getDefault(HttpServletRequest request) {

        //获取当前登录的用户id
        Long userId = (Long) request.getSession().getAttribute("user");

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(userId);

        if (null == addressBook) {
            return Result.error("没有找到该对象");
        } else {
            return Result.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public Result list(AddressBook addressBook,HttpServletRequest request) {
        //获取当前登录的用户id
        Long userId = (Long) request.getSession().getAttribute("user");
        addressBook.setUserId(userId);
        log.info("addressBook:{}", addressBook);


        //SQL:select * from address_book where user_id = ? order by update_time desc
        return Result.success(addressBookService.list(addressBook.getUserId()));
    }


    /*
    * 修改地址信息
    * */
    @PutMapping
    public Result alter(@RequestBody AddressBook addressBook){

        log.info("修改地址信息");

         addressBookService.alter(addressBook);

         return Result.success("修改成功");
    }


    /*
    * 删除地址
    * */
    @DeleteMapping
    public Result delete(@RequestParam Long ids){

        log.info("删除地址:{}",ids);

        addressBookService.delete(ids);

        return Result.success("删除成功");
    }

}
