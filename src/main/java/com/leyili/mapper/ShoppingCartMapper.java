package com.leyili.mapper;

import com.leyili.pojo.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    //SQL: select * from shopping_cart where user_id = ? and dish_id = ?
    @Select("select * from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    ShoppingCart getDishId(ShoppingCart shoppingCart);

    //SQL: select * from shopping_cart where user_id = ? and setmeal_id = ?
    @Select("select * from shopping_cart where user_id = #{userId} and setmeal_id =#{setmealId} ")
    ShoppingCart getSetmealId(ShoppingCart shoppingCart);

    //更新购物车的数据
    void updateById(ShoppingCart temp);

    //添加购物车
    @Insert("insert into shopping_cart(id, name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "VALUES (#{id},#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void save(ShoppingCart shoppingCart);

    //查看购物车
    @Select("select * from shopping_cart where user_id = #{userId} order by create_time")
    List<ShoppingCart> list(@Param("userId") Long userId);

    //清空购物车
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(@Param("userId") Long userId);

    //删除购物车记录
    void delete(ShoppingCart shoppingCart);
}
