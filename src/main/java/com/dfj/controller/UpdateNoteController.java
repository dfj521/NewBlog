package com.dfj.controller;

import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UpdateNoteController {

    @Resource
    private NoteService noteService;

    @RequestMapping("/note/update.do")
    public NoteResult execute(String noteId, String noteTitle, String noteBody) {
        return noteService.updateNote(noteId, noteTitle, noteBody);
    }
}
