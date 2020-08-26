package com.dfj.dao;

import com.dfj.entity.Book;
import com.dfj.util.NoteResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookDao {

    @Select("select * from cn_notebook where cn_user_id = #{userId} and cn_notebook_type_id != '2'")
    List<Book> findByUserId(String userId);

    @Insert("insert into cn_notebook(cn_notebook_id,cn_user_id,cn_notebook_type_id,cn_notebook_name,cn_notebook_createtime) " +
            "values(#{cn_notebook_id},#{cn_user_id},'5',#{cn_notebook_name},#{cn_notebook_createtime})")
    int save(Book book);

    @Update("<script>" +
            "update cn_notebook " +
            "<trim prefix='set' suffixOverrides=','> " +
            "<if test = 'cn_user_id != null'> " +
            "cn_user_id = #{cn_user_id},</if> " +
            "<if test = 'cn_notebook_type_id != null'> " +
            "cn_notebook_type_id = #{cn_notebook_type_id},</if> " +
            "<if test = 'cn_notebook_name != null'> " +
            "cn_notebook_name = #{cn_notebook_name},</if> " +
            "<if test = 'cn_notebook_desc != null'> " +
            "cn_notebook_desc = #{cn_notebook_desc},</if> " +
            "<if test = 'cn_notebook_createtime != null'> " +
            "cn_notebook_createtime = #{cn_notebook_createtime},</if>" +
            "</trim>" +
            "where cn_notebook_id = #{cn_notebook_id}" +
            "</script>")
    int update(Book book);
}
