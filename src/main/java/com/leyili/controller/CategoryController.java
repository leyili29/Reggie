package com.leyili.controller;


import com.leyili.pojo.Category;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
* 分类管理
* */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //新增分类
    @PostMapping
    public Result save(HttpServletRequest request, @RequestBody Category category){
        log.info("category:{}",category);

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        category.setCreateUser(empId);
        category.setUpdateUser(empId);

        categoryService.save(category);

        return Result.success("新增分类成功");
    }

    //实现分页查询
    @GetMapping("/page")
    public Result page(Integer page, Integer pageSize){
        log.info("分页查询,参数{},{}",page,pageSize);

        PageBean pageBean = categoryService.page(page,pageSize);

        return Result.success(pageBean);
    }

    //根据id删除分类
    @DeleteMapping
    public Result delete(Long ids){
        log.info("删除分类:{}",ids);

        categoryService.deleteById(ids);

        return Result.success("分类信息删除成功");
    }

    //更新分类信息
    @PutMapping
    public Result update(HttpServletRequest request,@RequestBody Category category){
        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        category.setUpdateUser(empId);

        categoryService.update(category);

        return Result.success("修改成功");
    }


    //根据类型查询
    @GetMapping("/list")
    public Result list(Category category){
       List<Category> categoryList= categoryService.list(category);

       return  Result.success(categoryList);
    }
}