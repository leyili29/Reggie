package com.leyili.service.impl;

import com.leyili.mapper.OrderDetailMapper;
import com.leyili.pojo.OrderDetail;
import com.leyili.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    @Autowired
    private OrderDetailMapper orderDetailMapper;


    /*
    * 批量插入
    * */
    @Override
    public void saveBatch(List<OrderDetail> orderDetails) {
         orderDetailMapper.saveBatch(orderDetails);
    }

    //根据订单id查询明细菜品
    @Override
    public List<OrderDetail> getByOrderId(Long orderId) {
        return orderDetailMapper.getByOrderId(orderId);
    }
}
