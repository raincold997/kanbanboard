package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    void create(KBUser user);

    KBUser findById(Long userId);

    KBUser findByEmail(String email);

    KBUser findByName(String name);

    void update(KBUser user);

    Set<KBBoard> getBoardList(Long userId);

    void deleteBoard(Long userId,Long boardId);

    String getNameById(Long userId);

}
