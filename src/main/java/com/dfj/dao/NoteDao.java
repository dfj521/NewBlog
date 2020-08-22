package com.dfj.dao;

import com.dfj.entity.Note;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoteDao {

    @Select("select cn_note_id,cn_note_title,cn_note_type_id from cn_note " +
            "where cn_notebook_id = #{bookId} and cn_note_status_id = '1'")
    List<Note> findByBookId(String bookId);
}
