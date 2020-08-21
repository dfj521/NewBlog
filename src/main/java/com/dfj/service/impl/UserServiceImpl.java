package com.dfj.service.impl;

import com.dfj.dao.UserDao;
import com.dfj.entity.User;
import com.dfj.service.UserService;
import com.dfj.util.NoteResult;
import com.dfj.util.NoteUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public NoteResult checkLogin(String name, String password) {
        NoteResult noteResult = new NoteResult();
        //判断用户名存在
        User user = userDao.findByName(name);
        if (user == null) {
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return noteResult;
        }
        //判断密码
        try {
            String md5_pwd = NoteUtil.md5(password);
            if (!user.getCn_user_password().equals(md5_pwd)) {
                noteResult.setStatus(2);
                noteResult.setMsg("密码错误");
                return noteResult;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //登录成功
        noteResult.setStatus(0);
        noteResult.setMsg("登录成功");
        //把密码信息屏蔽掉
        user.setCn_user_password("");
        noteResult.setData(user);
        return noteResult;
    }
}
