package com.leyili.dto;

import com.leyili.pojo.PageBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBeanDto extends PageBean {
    
    private Integer pages;

    public PageBeanDto(long total, List<OrdersDto> result, int pages) {
          this.pages = pages;
          this.setTotal(total);
          this.setRecords(result);
    }
}
