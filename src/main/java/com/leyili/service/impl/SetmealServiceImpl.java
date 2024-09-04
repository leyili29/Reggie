package com.leyili.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import com.leyili.dto.SetmealDto;
import com.leyili.exception.CustomException;
import com.leyili.mapper.CategoryMapper;
import com.leyili.mapper.SetmealDishMapper;
import com.leyili.mapper.SetmealMapper;
import com.leyili.pojo.PageBean;
import com.leyili.pojo.Result;
import com.leyili.pojo.Setmeal;
import com.leyili.pojo.SetmealDish;
import com.leyili.service.SetmealService;
import com.leyili.utils.GenerateID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private CategoryMapper categoryMapper;



    //根据分类id查询套餐
    @Override
    public int getByCategoryId(Long ids) {
        return setmealMapper.getByCategoryId(ids);
    }

    //添加套餐
    @Override
    public void save(SetmealDto setmealDto) {

        //设置基本信息
        setmealDto.setId(GenerateID.generateEmployeeID(setmealDto.getName()));
        Date now = new Date();
        setmealDto.setCreateTime(now);
        setmealDto.setUpdateTime(now);
        setmealDto.setIsDeleted(0);

        setmealMapper.insert(setmealDto);

    }

    //套餐分页查询
    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
        //构造分页构造器
        PageHelper.startPage(page,pageSize);

        //查询数据
        List<SetmealDto> setmealist = setmealMapper.page(name);

        //设置分类套餐名称
        for(SetmealDto setmeal : setmealist){
            //获取当前套餐的分类id
            Long categoryId = setmeal.getCategoryId();

            if(categoryId!=null){
                 setmeal.setCategoryName(categoryMapper.getNameById(categoryId));
            }
        }

        Page<SetmealDto> p = (Page<SetmealDto>)setmealist;

        return new PageBean(p.getTotal(),p.getResult());
    }

    //批量起售停售
    @Override
    public void status(Integer status, List<Long> ids, Long empId) {
         Date now = new Date();
         setmealMapper.status(status,ids,empId,now);
    }


    //实现批量删除
    @Override
    public void delete(List<Long> ids) {

        //判断是否售卖,若售卖中 则抛出异常
        int count = setmealMapper.checkStatus(ids);

        if(count>0){
            throw new CustomException("套餐正在售卖中,不能删除");
        }

        //删除
        setmealMapper.delete(ids);
    }

    //根据id获取套餐信息
    @Override
    public SetmealDto getById(Long id) {

        SetmealDto setmealDto = setmealMapper.getById(id);

        log.info("根据套餐id查询菜品信息");
        List<SetmealDish> setmealDishes = setmealDishMapper.getById(id);

        setmealDto.setSetmealDishes(setmealDishes);

        return setmealDto;
    }

    //修改setmeal表 套餐的基本信息
    @Override
    public void update(SetmealDto setmealDto) {

        //设置初始值
        Date now = new Date();
        setmealDto.setUpdateTime(now);
        setmealDto.setIsDeleted(0);

        setmealMapper.update(setmealDto);

    }

    // 根据分类id查询套餐
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }


}
