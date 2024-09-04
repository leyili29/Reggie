package com.leyili.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyili.exception.CustomException;
import com.leyili.mapper.CategoryMapper;
import com.leyili.pojo.Category;
import com.leyili.pojo.PageBean;
import com.leyili.service.CategoryService;
import com.leyili.service.DishService;
import com.leyili.service.SetmealService;
import com.leyili.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    //新增分类
    @Override
    public void save(Category category) {
        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        //生成分类id
        category.setId(GenerateID.generateEmployeeID(category.getName()));

        categoryMapper.insert(category);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询
         List<Category> categoryList= categoryMapper.list();
         Page<Category> p = (Page<Category>) categoryList;

        return new PageBean(p.getTotal(),p.getResult());
    }

    /*
    * 根据id删除分类,删除之前需要进行判断
    * */
    @Override
    public void deleteById(Long ids) {

        //查询当前分类是否关联了菜品,如果已经关联,抛出一个业务异常
         int count1 = dishService.getByCategoryId(ids);

         if(count1>0){
             //已经关联菜品,抛出一个业务异常
             throw new CustomException("当前分类下关联了菜品,不能删除");
         }

        //查询当前分类是否关联了套餐,如果已经关联,抛出一个业务异常
         int count2 = setmealService.getByCategoryId(ids);

         if(count2>0){
             //已经关联套餐,抛出一个业务异常
             throw new CustomException("当前分类下关联了套餐,不能删除");
         }

        //正常删除分类
        categoryMapper.deleteById(ids);
    }

    //更新分类信息
    @Override
    public void update(Category category) {
        Date now = new Date();
        category.setUpdateTime(now);

        categoryMapper.update(category);
    }

    //根据类型查询
    @Override
    public List<Category> list(Category category) {
        return categoryMapper.getByType(category);
    }


}
