package com.dfj.controller;

import com.dfj.service.UserService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/login.do")
    public NoteResult login(String name, String password) {
        return userService.checkLogin(name, password);
    }

    @RequestMapping("/add.do")
    public NoteResult add(String name, String nick, String password) {
        return userService.addUser(name, nick, password);
    }
}
