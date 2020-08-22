package com.dfj.service.impl;

import com.dfj.dao.BookDao;
import com.dfj.entity.Book;
import com.dfj.service.BookService;
import com.dfj.util.NoteResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookDao bookDao;

    @Override
    public NoteResult loadUserBooks(String userId) {
        NoteResult noteResult = new NoteResult();
        List<Book> books = bookDao.findByUserId(userId);
        //创建返回结果
        noteResult.setStatus(0);
        noteResult.setMsg("查询笔记成功");
        noteResult.setData(books);
        return noteResult;
    }
}
