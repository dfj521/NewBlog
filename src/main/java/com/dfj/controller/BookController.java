package com.dfj.controller;

import com.dfj.service.BookService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @RequestMapping("/loadbooks.do")
    public NoteResult loadbooks(String userId) {
        return bookService.loadUserBooks(userId);
    }

    @RequestMapping("/update.do")
    public NoteResult update(String bookId, String bookName) {
        return bookService.updateBook(bookId, bookName);
    }

    @RequestMapping("/add.do")
    public NoteResult add(String bookName, String userId) {
        return bookService.addBook(bookName, userId);
    }

    @RequestMapping("/delete.do")
    public NoteResult delete(String bookId) {
        return bookService.deleteBook(bookId);
    }

    @RequestMapping("/like.do")
    public NoteResult like(String bookId) {
        return bookService.likeBook(bookId);
    }
}
