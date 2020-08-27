package com.dfj.dao;

import com.dfj.entity.Share;
import com.dfj.util.PageUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShareDao {

    @Insert("<script> " +
            "insert into cn_share " +
            "(<trim suffixOverrides=','> " +
            "<if test = 'cn_share_id != null and cn_share_id != \"\"'> " +
            "cn_share_id,</if> " +
            "<if test = 'cn_share_title != null and cn_share_title != \"\"'> " +
            "cn_share_title,</if> " +
            "<if test = 'cn_share_body != null and cn_share_body != \"\"'> " +
            "cn_share_body,</if> " +
            "<if test = 'cn_note_id != null and cn_note_id != \"\"'> " +
            "cn_note_id,</if> " +
            "</trim>) " +
            "values ( " +
            "<trim suffixOverrides=','> " +
            "<if test = 'cn_share_id != null and cn_share_id != \"\"'> " +
            "#{cn_share_id},</if> " +
            "<if test = 'cn_share_title != null and cn_share_title != \"\"'> " +
            "#{cn_share_title},</if> " +
            "<if test = 'cn_share_body != null and cn_share_body != \"\"'> " +
            "#{cn_share_body},</if> " +
            "<if test = 'cn_note_id != null and cn_note_id != \"\"'> " +
            "#{cn_note_id},</if> " +
            "</trim>)" +
            "</script>")
    int save(Share share);

    @Select("<script>" +
            "select cn_share_id,cn_share_title,cn_share_body,cn_note_id from cn_share " +
            "<where> " +
            "<bind name='name' value=\"'%' + name + '%'\" />" +
            "<if test='name != null and name != \"\"'>" +
            "AND cn_share_title like #{name}</if>" +
            "</where>" +
            "limit #{start},#{count}" +
            "</script>")
    List<Share> search(PageUtil pageUtil);

    @Select("<script> " +
            "select cn_share_id,cn_share_title,cn_share_body,cn_note_id from cn_share " +
            "<where> " +
            "<if test = 'cn_share_id != null and cn_share_id != \"\"'> " +
            "cn_share_id = #{cn_share_id}</if> " +
            "<if test = 'cn_share_title != null and cn_share_title != \"\"'> " +
            "and cn_share_title = #{cn_share_title}</if> " +
            "<if test = 'cn_share_body != null and cn_share_body != \"\"'> " +
            "and cn_share_body = #{cn_share_body}</if> " +
            "<if test = 'cn_note_id != null and cn_note_id != \"\"'> " +
            "and cn_note_id = #{cn_note_id}</if> " +
            "</where> " +
            "</script>")
    Share query(Share share);
}
