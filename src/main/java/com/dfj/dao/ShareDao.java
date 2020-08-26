package com.dfj.dao;

import com.dfj.entity.Share;
import org.apache.ibatis.annotations.Insert;

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
}
