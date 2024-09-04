package com.leyili.service;


import com.leyili.pojo.Category;
import com.leyili.pojo.PageBean;

import java.util.List;

public interface CategoryService {

    //新增分类
    void save(Category category);

    //实现分页查询
    PageBean page(Integer page, Integer pageSize);

    //根据id删除分类
    void deleteById(Long ids);

    //更新分类信息
    void update(Category category);

    //根据类型查询
    List<Category> list(Category category);

}
