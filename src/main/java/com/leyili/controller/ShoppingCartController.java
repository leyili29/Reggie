package com.leyili.controller;

import com.leyili.pojo.Result;
import com.leyili.pojo.ShoppingCart;
import com.leyili.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /*
    * 添加购物车
    * */
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        log.info("添加购物车:{}",shoppingCart);


        //设置用户id,指定当前是哪个用户的购物车数据
        Long userId = (Long) request.getSession().getAttribute("user");
        shoppingCart.setUserId(userId);

        //查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();

        ShoppingCart temp;


        if(dishId!=null){
            //添加到购物车的是菜品
            //SQL: select * from shopping_cart where user_id = ? and dish_id = ?
            temp = shoppingCartService.getDishId(shoppingCart);
        }
        else {
            //添加到购物车的是套餐
            //SQL: select * from shopping_cart where user_id = ? and setmeal_id = ?
             temp = shoppingCartService.getSetmealId(shoppingCart);
        }


        //如果已经存在,就在原来数量基础上加一
              if(temp!=null){
                  Integer number =  temp.getNumber();
                  temp.setNumber(number+1);
                  shoppingCartService.updateById(temp);
              }
              else {
                  //如果不存在，则添加到购物车，数量默认是一
                  shoppingCart.setNumber(1);
                  shoppingCartService.save(shoppingCart);
                  temp = shoppingCart;
              }

        return Result.success(temp);
    }

    /*
    * 查看购物车
    * */
    @GetMapping("/list")
    public Result list(HttpServletRequest request){
        log.info("查看购物车");

        //获取当前登录用户
        Long userId = (Long) request.getSession().getAttribute("user");
        List<ShoppingCart>  shoppingCartList = shoppingCartService.list(userId);

        return Result.success(shoppingCartList);
    }


    /*
    * 清空购物车
    * */
    @DeleteMapping("/clean")
    public Result clean(HttpServletRequest request){

        //SQL: delete from shopping_cart where user_id = ?
        //获取当前登录用户
        Long userId = (Long) request.getSession().getAttribute("user");

        shoppingCartService.clean(userId);


        return Result.success("清空购物车成功");
    }

    /*
       减少购物车商品数量 如过数量等于1 则从购物车表删除该商品 否则number=number-1
    * */
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCart shoppingCart,HttpServletRequest request){

        //获取当前登录用户
        Long userId = (Long) request.getSession().getAttribute("user");
        shoppingCart.setUserId(userId);


          //操作的对象是菜品
          if(shoppingCart.getDishId()!=null){
              shoppingCart = shoppingCartService.getDishId(shoppingCart);
          }
          else {
              //操作的对象是套餐
              shoppingCart =  shoppingCartService.getSetmealId(shoppingCart);
          }

        if(shoppingCart.getNumber()>1){
              //数量大于一 number= number-1
            shoppingCart.setNumber(shoppingCart.getNumber()-1);
            shoppingCartService.updateById(shoppingCart);
        }
        else {
            //数量等于1 删除该条购物车记录

            //Sql: delete from shopping_cart where user_id = ？ and  dish_id/setmeal_id = ？
            shoppingCartService.delete(shoppingCart);

            return Result.success("数量为0,从购物车中删除该商品");
        }
        return Result.success(shoppingCart);
    }

}
