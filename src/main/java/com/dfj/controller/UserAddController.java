package com.dfj.controller;

import com.dfj.service.UserService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserAddController {

    @Resource
    private UserService userService;

    @RequestMapping("/user/add.do")
    public NoteResult execute(String name, String nick, String password) {
        return userService.addUser(name, nick, password);
    }
}
