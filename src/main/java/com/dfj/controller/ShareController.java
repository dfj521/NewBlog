package com.dfj.controller;

import com.dfj.service.ShareService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Resource
    private ShareService shareService;

    @RequestMapping("/add.do")
    public NoteResult add(String noteId) {
        return shareService.addShare(noteId);
    }

    @RequestMapping("/search.do")
    public NoteResult search(String keyword, Integer page) {
        return shareService.searchShare(keyword, page);
    }

    @RequestMapping("/loadShareNote.do")
    public NoteResult loadShareNote(String shareId) {
        return shareService.loadShareNote(shareId);
    }
}
