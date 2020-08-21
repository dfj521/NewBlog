package com.dfj.dao;

import com.dfj.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select * from cn_user where cn_user_name = #{name}")
    User findByName(String name);
}
