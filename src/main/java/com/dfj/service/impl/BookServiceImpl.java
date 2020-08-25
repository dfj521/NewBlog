package com.dfj.service.impl;

import com.dfj.dao.BookDao;
import com.dfj.entity.Book;
import com.dfj.service.BookService;
import com.dfj.util.NoteResult;
import com.dfj.util.NoteUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    @Override
    public NoteResult addBook(String bookName, String userId) {
        NoteResult noteResult = new NoteResult();
        Book book = new Book();
        book.setCn_notebook_id(NoteUtil.createId());
        book.setCn_user_id(userId);
        book.setCn_notebook_name(bookName);
        book.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
        int i = bookDao.save(book);
        if (i > 0) {
            noteResult.setStatus(0);
            noteResult.setMsg("创建笔记本成功");
            noteResult.setData(book.getCn_notebook_id());
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("创建笔记本失败");
        }
        return noteResult;
    }

    @Override
    public NoteResult updateBook(String bookId, String bookName) {
        NoteResult noteResult = new NoteResult();
        Book book = new Book();
        book.setCn_notebook_id(bookId);
        book.setCn_notebook_name(bookName);
        int i = bookDao.update(book);
        if (i > 0) {
            noteResult.setStatus(0);
            noteResult.setMsg("重命名成功");
        } else {
            noteResult.setStatus(1);
            noteResult.setMsg("重命名失败");
        }
        return noteResult;
    }
}
