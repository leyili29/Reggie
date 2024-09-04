package com.leyili.service;

import com.leyili.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    //批量插入
    void saveBatch(List<OrderDetail> orderDetails);

    //根据订单id查询明细菜品
    List<OrderDetail> getByOrderId(Long orderId);
}

