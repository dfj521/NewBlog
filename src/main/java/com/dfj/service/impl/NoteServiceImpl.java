package com.dfj.service.impl;

import com.dfj.dao.NoteDao;
import com.dfj.entity.Note;
import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import com.dfj.util.NoteUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteDao noteDao;

    @Override
    public NoteResult loadNotes(String bookId) {
        NoteResult noteResult = new NoteResult();
        List<Note> notes = noteDao.findByBookId(bookId);
        noteResult.setStatus(0);
        noteResult.setMsg("查询笔记成功");
        noteResult.setData(notes);
        return noteResult;
    }

    @Override
    public NoteResult loadNote(String noteId) {
        NoteResult noteResult = new NoteResult();
        Note note = noteDao.findById(noteId);
        noteResult.setStatus(0);
        noteResult.setMsg("查询笔记内容成功");
        noteResult.setData(note);
        return noteResult;
    }

    @Override
    public NoteResult updateNote(String noteId, String noteTitle, String noteBody) {
        NoteResult noteResult = new NoteResult();
        Note note = new Note();
        note.setCn_note_id(noteId);
        note.setCn_note_title(noteTitle);
        note.setCn_note_body(noteBody);
        //笔记修改时间
        note.setCn_note_last_modify_time(System.currentTimeMillis());
        int i = noteDao.updateNote(note);
        if (i > 0) { //成功
            noteResult.setStatus(0);
            noteResult.setMsg("保存笔记成功");
        } else { //失败
            noteResult.setStatus(1);
            noteResult.setMsg("保存笔记失败");
        }
        return noteResult;
    }

    @Override
    public NoteResult addNote(String bookId, String userId, String noteTitle) {
        NoteResult noteResult = new NoteResult();
        Note note = new Note();
        note.setCn_note_id(NoteUtil.createId());
        note.setCn_notebook_id(bookId);
        note.setCn_user_id(userId);
        note.setCn_note_title(noteTitle);
        note.setCn_note_status_id("1");
        note.setCn_note_type_id("1");
        note.setCn_note_body("");
        note.setCn_note_create_time(System.currentTimeMillis());
        note.setCn_note_last_modify_time(System.currentTimeMillis());
        int i = noteDao.save(note);
        if (i > 0) {
            noteResult.setStatus(0);
            noteResult.setMsg("创建笔记成功");
            noteResult.setData(note.getCn_note_id());
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("创建笔记失败");
        }
        return noteResult;
    }
}
