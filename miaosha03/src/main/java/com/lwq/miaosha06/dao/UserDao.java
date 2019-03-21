package com.lwq.miaosha06.dao;

import com.lwq.miaosha06.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 23:17
 * @Version 1.0
 * @Describe
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);
}
