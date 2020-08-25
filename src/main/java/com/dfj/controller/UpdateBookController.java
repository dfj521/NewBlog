package com.dfj.controller;

import com.dfj.service.BookService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UpdateBookController {

    @Resource
    private BookService bookService;

    @RequestMapping("/book/update.do")
    public NoteResult execute(String bookId, String bookName) {
        return bookService.updateBook(bookId, bookName);
    }
}
