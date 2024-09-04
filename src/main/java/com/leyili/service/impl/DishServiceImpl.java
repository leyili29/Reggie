package com.leyili.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyili.dto.DishDto;
import com.leyili.mapper.CategoryMapper;
import com.leyili.mapper.DishFlavorMapper;
import com.leyili.mapper.DishMapper;
import com.leyili.mapper.SetmealDishMapper;
import com.leyili.pojo.Dish;
import com.leyili.pojo.DishFlavor;
import com.leyili.pojo.PageBean;
import com.leyili.service.DishService;
import com.leyili.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //根据分类id查询菜品
    @Override
    public int getByCategoryId(Long ids) {
        return dishMapper.getByCategoryId(ids);
    }

    //添加菜品
    @Override
    public void save(DishDto dishDto) {
        Date now = new Date();
        dishDto.setCreateTime(now);
        dishDto.setUpdateTime(now);

        dishDto.setSort(0);
        dishDto.setIsDeleted(0);

        dishMapper.insert(dishDto);

    }

    //实现分页查询
    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page,pageSize);

        //查询数据
        List<DishDto> dishList = dishMapper.list(name);

        //设置类别名称
        for (DishDto dish : dishList) {
            Long categoryId = dish.getCategoryId();
            if (categoryId != null) {
                dish.setCategoryName(categoryMapper.getNameById(categoryId));
            }
        }

        Page<DishDto> p = (Page<DishDto>) dishList;

        return new PageBean(p.getTotal(),p.getResult());
    }

    //根据id查询菜品信息
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息,dish
        DishDto dishDto = dishMapper.getById(id);

        //查询对应口味信息,dish_flavor
        List<DishFlavor> dishFlavors = dishFlavorMapper.getById(id);
        dishDto.setFlavors(dishFlavors);

        return dishDto;
    }

    //修改菜品
    @Override
    public void update(DishDto dishDto) {
         Date now = new Date();
         dishDto.setUpdateTime(now);

         dishMapper.update(dishDto);
    }

    @Override
    public DishDto getById(Long id) {
        //查询菜品基本信息,dish
        return dishMapper.getById(id);
    }

    //批量起售停售
    @Override
    @Transactional
    public void status(HttpServletRequest request,Integer status, List<Long> ids, Long empId) {
        Date now = new Date();
        dishMapper.status(status,ids,empId,now);

        //如果停售菜品 如果该菜品对应有套餐 则套餐也进行停售
        if(status==0){
            //获得当前登录用户的id
            Long updateUser = (Long)request.getSession().getAttribute("employee");

            //select setmeal_id from setmeal_dish where dish_id in (ids)

            List<Long> setmealIds = setmealDishMapper.getSetmealByDish(ids);

            if(!setmealIds.isEmpty()){
                setmealService.status(status,setmealIds,updateUser);
            }
        }
    }

    //实现批量删除
    @Override
    public void delete(List<Long> ids) {
         dishMapper.delete(ids);
    }


    //根据分类查询菜品
    @Override
    public List<DishDto> list(Dish dish) {

        //查询菜品的基本信息
        List<DishDto> dishList = dishMapper.listByCategory(dish);

        //查询菜品的口味信息
        for (DishDto temp : dishList) {
              temp.setFlavors(dishFlavorMapper.getById(temp.getId()));
        }

        return dishList;
    }


}
