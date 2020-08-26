package com.dfj.service;

import com.dfj.util.NoteResult;

public interface NoteService {

    NoteResult loadNotes(String bookId);

    NoteResult loadNote(String noteId);

    NoteResult updateNote(String noteId, String noteTitle, String noteBody);

    NoteResult addNote(String bookId, String userId, String noteTitle);

    NoteResult deleteNote(String noteId);

    NoteResult moveNote(String bookId, String noteId);
}
