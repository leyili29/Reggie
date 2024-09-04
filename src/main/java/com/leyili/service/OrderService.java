package com.leyili.service;

import com.leyili.dto.PageBeanDto;
import com.leyili.pojo.Orders;
import com.leyili.pojo.PageBean;

import java.util.Date;

public interface OrderService {

      //用户下单
      void submit(Orders orders);

      //管理端订单明细分页查询
      PageBean page(Integer page, Integer pageSize, String number, String beginTime, String endTime);

      //管理端订单状态修改
      void status(Orders orders);

      //用户端订单明细分页查询
      PageBeanDto pageUser(Integer page, Integer pageSize);
}
