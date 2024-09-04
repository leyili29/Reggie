package com.leyili.service;

import com.leyili.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    //SQL: select * from shopping_cart where user_id = ? and dish_id = ?
    ShoppingCart getDishId(ShoppingCart shoppingCart);

    //SQL: select * from shopping_cart where user_id = ? and setmeal_id = ?
    ShoppingCart getSetmealId(ShoppingCart shoppingCart);

    //更新购物车的数据
    void updateById(ShoppingCart temp);

    //添加购物车
    void save(ShoppingCart shoppingCart);

    //查看购物车
    List<ShoppingCart> list(Long userId);

    //清空购物车
    void clean(Long userId);

    //删除一条购物车记录
    void delete(ShoppingCart shoppingCart);
}
