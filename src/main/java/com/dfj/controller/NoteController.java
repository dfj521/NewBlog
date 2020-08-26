package com.dfj.controller;

import com.dfj.service.NoteService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Resource
    private NoteService noteService;

    @RequestMapping("/loadNotes.do")
    public NoteResult loadNotes(String bookId) {
        return noteService.loadNotes(bookId);
    }

    @RequestMapping("/update.do")
    public NoteResult update(String noteId, String noteTitle, String noteBody) {
        return noteService.updateNote(noteId, noteTitle, noteBody);
    }

    @RequestMapping("/loadNote.do")
    public NoteResult loadNote(String noteId) {
        return noteService.loadNote(noteId);
    }

    @RequestMapping("/add.do")
    public NoteResult add(String bookId, String userId, String noteTitle) {
        return noteService.addNote(bookId, userId, noteTitle);
    }

    @RequestMapping("/delete.do")
    public NoteResult delete(String noteId) {
        return noteService.deleteNote(noteId);
    }

    @RequestMapping("/move.do")
    public NoteResult move(String bookId, String noteId) {
        return noteService.moveNote(bookId, noteId);
    }
}
