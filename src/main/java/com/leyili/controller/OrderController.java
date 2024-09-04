package com.leyili.controller;


import com.leyili.dto.PageBeanDto;
import com.leyili.pojo.Orders;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
    * 用户下单
    * */
    @PostMapping("/submit")
    @Transactional
    public Result submit(@RequestBody Orders orders, HttpServletRequest request){

        //获得当前登录用户的id
        Long userId = (Long)request.getSession().getAttribute("user");
        orders.setUserId(userId);

        log.info("订单数据:{}",orders);

        orderService.submit(orders);

        return Result.success("下单成功");
    }


    /*
    * 管理端订单明细分页查询
    * */
    @GetMapping("/page")
    public Result page(Integer page, Integer pageSize, String number, String beginTime,String endTime){

        log.info(" 管理端订单明细分页查询");

        PageBean pageBean = orderService.page(page,pageSize,number,beginTime,endTime);

        return Result.success(pageBean);
    }


    /*
    * 管理端订单状态修改
    * */
    @PutMapping
    public Result order(@RequestBody Orders orders){

         log.info("管理端订单派送");

         orderService.status(orders);

        return Result.success("状态修改成功");
    }

    /*
    * 用户端订单明细分页查询
    * */
    @GetMapping("/userPage")
    public Result userPage(Integer page, Integer pageSize){
        log.info("用户端订单明细分页查询");

        PageBeanDto pageBeanDto = orderService.pageUser(page,pageSize);

        return Result.success(pageBeanDto);
    }


}
