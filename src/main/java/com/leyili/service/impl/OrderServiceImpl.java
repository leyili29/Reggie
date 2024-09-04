package com.leyili.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyili.dto.OrdersDto;
import com.leyili.dto.PageBeanDto;
import com.leyili.exception.CustomException;
import com.leyili.mapper.OrderMapper;
import com.leyili.pojo.*;
import com.leyili.service.*;
import com.leyili.utils.GenerateID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    //用户下单
    @Override
    public void submit(Orders orders) {

        Date now = new Date();
        orders.setOrderTime(now);

        //查询当前用户的购物车数据
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(orders.getUserId());
        if(shoppingCarts.isEmpty()){
            throw new CustomException("购物车为空,不能下单");
        }

        //查询用户数据
        User user = userService.getById(orders.getUserId());

        //查询地址数据
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());

        if(addressBook == null){
            throw new CustomException("用户地址信息有误,不能下单");
        }

        orders.setNumber(String.valueOf(GenerateID.generateEmployeeID(String.valueOf(user.getId())+addressBook.getId()+orders.getOrderTime())));
        orders.setId(GenerateID.generateEmployeeID(orders.getUserId()+orders.getNumber()));

        AtomicInteger amount = new AtomicInteger(0);

       List<OrderDetail> orderDetails = shoppingCarts.stream().map((item)->{
           OrderDetail orderDetail = new OrderDetail();
           orderDetail.setOrderId(orders.getId());
           orderDetail.setNumber(item.getNumber());
           orderDetail.setDishFlavor(item.getDishFlavor());
           orderDetail.setDishId(item.getDishId());
           orderDetail.setSetmealId(item.getSetmealId());
           orderDetail.setName(item.getName());
           orderDetail.setImage(item.getImage());
           orderDetail.setAmount(item.getAmount());
           amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
           orderDetail.setId(GenerateID.generateEmployeeID(item.getUserId()+orders.getId()+item.getName()));
           return orderDetail;
        }).collect(Collectors.toList());

        //向订单表插入数据,一条数据
        orders.setCheckoutTime(now);
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));


        orderMapper.insert(orders);

        //向订单明细表插入数据,多条数据
        orderDetailService.saveBatch(orderDetails);


        //清空购物车数据
        shoppingCartService.clean(orders.getUserId());
    }

    //管理端订单明细分页查询
    @Override
    public PageBean page(Integer page, Integer pageSize, String number, String beginTime, String endTime) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //执行查询
        List<Orders> orders = orderMapper.list(number,beginTime,endTime);
        Page<Orders> p = (Page<Orders>) orders;

        return new PageBean(p.getTotal(),p.getResult());
    }

    //管理端订单状态修改
    @Override
    public void status(Orders orders) {
         orderMapper.status(orders);
    }

    //用户端订单明细分页查询
    @Override
    public PageBeanDto pageUser(Integer page, Integer pageSize) {

        //构造分页参数
        PageHelper.startPage(page,pageSize);

        //执行查询
        List<OrdersDto> ordersDtos = orderMapper.pageUser(page,pageSize);

        //设置订单的明细餐品
        for(OrdersDto item : ordersDtos){
             item.setOrderDetails(orderDetailService.getByOrderId(item.getId()));
        }


        Page<OrdersDto> p = (Page<OrdersDto>) ordersDtos;

        return new PageBeanDto(p.getTotal(),p.getResult(),p.getPages());
    }

}
