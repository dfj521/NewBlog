package com.dfj.controller;

import com.dfj.service.BookService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AddBookController {

    @Resource
    private BookService bookService;

    @RequestMapping("/book/add.do")
    public NoteResult execute(String bookName, String userId) {
        return bookService.addBook(bookName, userId);
    }
}
