package com.dfj.controller;

import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AddNoteController {

    @Resource
    private NoteService noteService;

    @RequestMapping("/note/add.do")
    public NoteResult execute(String bookId, String userId, String noteTitle) {
        return noteService.addNote(bookId, userId, noteTitle);
    }
}
