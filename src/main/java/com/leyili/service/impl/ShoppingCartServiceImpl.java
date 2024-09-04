package com.leyili.service.impl;

import com.leyili.mapper.ShoppingCartMapper;
import com.leyili.pojo.ShoppingCart;
import com.leyili.service.ShoppingCartService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    //SQL: select * from shopping_cart where user_id = ? and dish_id = ?
    @Override
    public ShoppingCart getDishId(ShoppingCart shoppingCart) {
        return shoppingCartMapper.getDishId(shoppingCart);
    }

    //SQL: select * from shopping_cart where user_id = ? and setmeal_id = ?
    @Override
    public ShoppingCart getSetmealId(ShoppingCart shoppingCart) {
        return shoppingCartMapper.getSetmealId(shoppingCart);
    }

    //更新购物车的数据
    @Override
    public void updateById(ShoppingCart temp) {
        shoppingCartMapper.updateById(temp);
    }

    //添加购物车
    @Override
    public void save(ShoppingCart shoppingCart) {
        Date now = new Date();
        shoppingCart.setCreateTime(now);

        Long tempId = shoppingCart.getDishId()!=null?shoppingCart.getDishId():shoppingCart.getSetmealId();
        shoppingCart.setId(GenerateID.generateEmployeeID(String.valueOf(shoppingCart.getUserId()+tempId)));

        shoppingCartMapper.save(shoppingCart);
    }

    //查看购物车
    @Override
    public List<ShoppingCart> list(Long userId) {
        return shoppingCartMapper.list(userId);
    }

    //清空购物车
    @Override
    public void clean(Long userId) {
          shoppingCartMapper.clean(userId);
    }

    //删除购物车记录
    @Override
    public void delete(ShoppingCart shoppingCart) {
         shoppingCartMapper.delete(shoppingCart);
    }

}
