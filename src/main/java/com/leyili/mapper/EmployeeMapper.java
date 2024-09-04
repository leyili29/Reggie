package com.leyili.mapper;

import com.leyili.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    //根据用户名查询员工
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(Employee employee);

    //新增部门
    @Insert("insert into employee(id,name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values (#{id},#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    //查询员工信息
    List<Employee> list(@Param("name") String name);

    //根据id更新信息
    void updateById(Employee employee);

    //根据id查询员工信息
    @Select("select * from employee where id = #{id}")
    Employee getById(@Param("id") Long id);
}
