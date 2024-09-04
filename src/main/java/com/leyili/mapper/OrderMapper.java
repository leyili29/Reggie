package com.leyili.mapper;

import com.leyili.dto.OrdersDto;
import com.leyili.pojo.Orders;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface OrderMapper {

    //插入数据
    @Insert("insert into orders(id, number, status,user_id,address_book_id, order_time, checkout_time,pay_method,amount, remark, phone, address, user_name, consignee) " +
            "values (#{id},#{number},#{status},#{userId},#{addressBookId},#{orderTime},#{checkoutTime},#{payMethod},#{amount},#{remark},#{phone},#{address},#{userName},#{consignee})")
    void insert(Orders orders);

    //管理端订单明细分页查询
    List<Orders> list(@Param("number") String number,@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    //管理端订单状态修改
    @Update("update orders set status = #{status} where id = #{id}")
    void status(Orders orders);

    //用户端订单明细分页查询
    @Select("select * from orders order by order_time DESC")
    List<OrdersDto> pageUser(Integer page, Integer pageSize);
}
