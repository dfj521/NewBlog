package com.dfj.service.impl;

import com.dfj.dao.NoteDao;
import com.dfj.dao.ShareDao;
import com.dfj.entity.Note;
import com.dfj.entity.Share;
import com.dfj.service.ShareService;
import com.dfj.util.NoteResult;
import com.dfj.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ShareServiceImpl implements ShareService {
    @Resource
    private NoteDao noteDao;

    @Resource
    private ShareDao shareDao;

    @Override
    @Transactional
    public NoteResult addShare(String noteId) {
        NoteResult noteResult = new NoteResult();
        if (noteId == null || "".equals(noteId)) {
            noteResult.setStatus(1);
            noteResult.setMsg("共享笔记失败");
            return noteResult;
        }
        Note note = noteDao.findById(noteId);
        if ("2".equals(note.getCn_note_type_id())) {
            noteResult.setStatus(2);
            noteResult.setMsg("笔记已经分享过了");
            return noteResult;
        }
        note.setCn_note_id(noteId);
        note.setCn_note_type_id("2");
        note.setCn_note_last_modify_time(System.currentTimeMillis());
        int updateNote = noteDao.updateNote(note);
        if (updateNote != 1) {
            noteResult.setStatus(1);
            noteResult.setMsg("笔记分享失败");
        }
        Share share = new Share();
        share.setCn_share_id(NoteUtil.createId());
        share.setCn_share_title(note.getCn_note_title());
        share.setCn_share_body(note.getCn_note_body());
        share.setCn_note_id(noteId);
        int i = shareDao.save(share);
        if (i == 1) {
            noteResult.setStatus(0);
            noteResult.setMsg("分享笔记成功");
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("分享笔记失败");
        }
        return noteResult;
    }
}
