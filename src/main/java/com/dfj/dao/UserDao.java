package com.dfj.dao;

import com.dfj.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("select * from cn_user")
    List<User> findAll();

    @Select("select * from cn_user where cn_user_name = #{cn_user_name}")
    User findByName(String cn_user_name);
}
