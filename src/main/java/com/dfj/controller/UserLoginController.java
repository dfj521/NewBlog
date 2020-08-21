package com.dfj.controller;

import com.dfj.service.UserService;
import com.dfj.util.NoteResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserLoginController {

    @Resource
    private UserService userService;

    @RequestMapping("/user/login.do")
    public NoteResult execute(String name, String password) {
        return userService.checkLogin(name, password);
    }
}
