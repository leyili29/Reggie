package com.leyili.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 地址簿
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //用户id
    private Long userId;


    //收货人
    private String consignee;


    //手机号
    private String phone;


    //性别 0 女 1 男
    private String sex;


    //省级区划编号
    private String provinceCode;


    //省级名称
    private String provinceName;


    //市级区划编号
    private String cityCode;


    //市级名称
    private String cityName;


    //区级区划编号
    private String districtCode;


    //区级名称
    private String districtName;


    //详细地址
    private String detail;


    //标签
    private String label;

    //是否默认 0 否 1是
    private Integer isDefault;

    //创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    //更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    //创建人
    private Long createUser;


    //修改人
    private Long updateUser;


    //是否删除
    private Integer isDeleted;
}
