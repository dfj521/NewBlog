package com.dfj.service;

import com.dfj.util.NoteResult;

public interface UserService {

    NoteResult checkLogin(String name, String password);
}
