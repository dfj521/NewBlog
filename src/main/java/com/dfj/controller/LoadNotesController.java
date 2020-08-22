package com.dfj.controller;

import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoadNotesController {

    @Resource
    private NoteService noteService;

    @RequestMapping("/note/loadNotes.do")
    public NoteResult execute(String bookId) {
        return noteService.loadNotes(bookId);
    }
}
