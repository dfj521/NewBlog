package com.dfj.service;

import com.dfj.util.NoteResult;

public interface BookService {

    NoteResult loadUserBooks(String userId);

    NoteResult addBook(String bookName, String userId);

    NoteResult updateBook(String bookId, String bookName);

    NoteResult deleteBook(String bookId);

    NoteResult likeBook(String bookId);
}
