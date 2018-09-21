package com.nico.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.nico.springboot.SuperMapper;
import com.nico.springboot.entity.User;

/**
 * User 表数据库控制层接口
 */
public interface UserMapper extends SuperMapper<User> {

    /**
     * 自定义注入方法
     */
    int deleteAll();
    
    /**
     * @Title: 直接写sql方法
     * @Description: 
     * @date 2018年7月19日  
     * @return        
     */
    @Select("select test_id as id, name, age, test_type from user")
    List<User> selectListBySQL();

    /**
     * 自定义注入方法
     */
    List<User> selectListByWrapper(@Param("ew") Wrapper wrapper);

}