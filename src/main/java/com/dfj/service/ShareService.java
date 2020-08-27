package com.dfj.service;

import com.dfj.util.NoteResult;

public interface ShareService {
    NoteResult addShare(String noteId);

    NoteResult searchShare(String keyword, Integer page);

    NoteResult loadShareNote(String shareId);
}
