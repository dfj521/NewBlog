package com.dfj.controller;

import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoadNoteController {

    @Resource
    private NoteService noteService;

    @RequestMapping("/note/loadNote.do")
    public NoteResult execute(String noteId) {
        return noteService.loadNote(noteId);
    }
}
