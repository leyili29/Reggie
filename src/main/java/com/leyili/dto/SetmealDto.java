package com.leyili.dto;

import com.leyili.pojo.Setmeal;
import com.leyili.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
