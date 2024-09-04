package com.leyili.controller;


import com.leyili.dto.DishDto;
import com.leyili.pojo.Dish;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.service.DishFlavorService;
import com.leyili.service.DishService;
import com.leyili.utils.GenerateID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



/*
* 菜品管理
* */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    //添加菜品
    @PostMapping
    @Transactional
    public Result save(HttpServletRequest request, @RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        dishDto.setUpdateUser(empId);
        dishDto.setCreateUser(empId);

        //生成菜品id
        dishDto.setId(GenerateID.generateEmployeeID(dishDto.getName()));

        //新增菜品,同时插入菜品对应的口味数据
        //dish
        dishService.save(dishDto);

        //dish_flavor
        dishFlavorService.save(dishDto);

        return Result.success("新增菜品成功");
    }

    //实现分页查询
    @GetMapping("/page")
    public Result page(Integer page,Integer pageSize,String name){
        log.info("分页查询,参数{},{},{}",page,pageSize,name);

        PageBean pageBean = dishService.page(page,pageSize,name);

        return Result.success(pageBean);
    }

    //根据id查询菜品信息和对应口味信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return Result.success(dishDto);
    }

    //修该菜品
    @PutMapping
    @Transactional
    public Result update(HttpServletRequest request, @RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        dishDto.setUpdateUser(empId);

        //修改菜品,同时修改菜品对应的口味数据
        //dish
        dishService.update(dishDto);

        //根据id查出当前修改过后的菜品信息
        DishDto temp = dishService.getById(dishDto.getId());

        dishDto.setCreateTime(temp.getCreateTime());
        dishDto.setCreateUser(temp.getCreateUser());

        //dish_flavor
        dishFlavorService.update(dishDto);

        return Result.success("修改菜品成功");
    }

    //批量起售停售
    @PostMapping("/status/{status}")
    public Result status(HttpServletRequest request,@PathVariable Integer status,@RequestParam List<Long> ids){

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        dishService.status(request,status,ids,empId);

        return Result.success("菜品状态修改成功");
    }

    //实现批量删除
     @DeleteMapping
     @Transactional
     public Result delete(@RequestParam List<Long> ids){

        //删除dish表上的信息
         dishService.delete(ids);

         //删除dish_flavor上的信息
         dishFlavorService.delete(ids);

        return Result.success("删除成功");
     }

/*     //根据分类查询菜品
     @GetMapping("/list")
     public Result list(Dish dish){

        List<Dish> dishList = dishService.list(dish);

        return Result.success(dishList);
     }*/

    //根据分类查询菜品
    @GetMapping("/list")
    public Result list(Dish dish){

       // List<Dish> dishList = dishService.list(dish);

        List<DishDto> dishDtoList =dishService.list(dish);

        return Result.success(dishDtoList);
    }



}
