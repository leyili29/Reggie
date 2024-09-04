package com.leyili.mapper;

import com.leyili.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /*
       批量插入
    * */
    void saveBatch(@Param("orderDetails") List<OrderDetail> orderDetails);

    //根据订单id查询明细菜品
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(@Param("orderId") Long orderId);
}
