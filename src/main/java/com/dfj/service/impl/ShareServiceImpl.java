package com.dfj.service.impl;

import com.dfj.dao.NoteDao;
import com.dfj.dao.ShareDao;
import com.dfj.entity.Note;
import com.dfj.entity.Share;
import com.dfj.service.ShareService;
import com.dfj.util.NoteResult;
import com.dfj.util.NoteUtil;
import com.dfj.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
            noteResult.setStatus(2);
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

    @Override
    public NoteResult searchShare(String keyword, Integer page) {
        NoteResult noteResult = new NoteResult();
        if (keyword == null || page == null || page < 1) {
            noteResult.setStatus(2);
            noteResult.setMsg("搜索分享笔记失败");
            return noteResult;
        }
        PageUtil pageUtil = new PageUtil();
        pageUtil.setName(keyword);
        pageUtil.setStart((page - 1) * 18);
        pageUtil.setCount(18);
        /*Map<String, Object> map = new HashMap<>();
        keyword = "%"+keyword+"%";
        map.put("keyword",keyword);
        page = (page - 1) * 5;
        map.put("page",page);*/
        List<Share> shares = shareDao.search(pageUtil);
        if (shares != null) {
            noteResult.setStatus(0);
            noteResult.setMsg("搜索分享笔记成功");
            noteResult.setData(shares);
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("搜索分享笔记失败");
        }
        return noteResult;
    }

    @Override
    public NoteResult loadShareNote(String shareId) {
        NoteResult noteResult = new NoteResult();
        if (shareId == null || "".equals(shareId)) {
            noteResult.setStatus(2);
            noteResult.setMsg("加载分享笔记内容失败");
            return noteResult;
        }
        Share oldShare = new Share();
        oldShare.setCn_share_id(shareId);
        Share share = shareDao.query(oldShare);
        if (share != null) {
            noteResult.setStatus(0);
            noteResult.setMsg("加载分享笔记内容成功");
            noteResult.setData(share);
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("加载分享笔记内容失败");
        }
        return noteResult;
    }
}
