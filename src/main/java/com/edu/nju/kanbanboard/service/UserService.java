package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBUser;

public interface UserService {

    void create(KBUser user);

    KBUser findByEmail(String email);

    KBUser findByName(String name);

    void update(KBUser user);
}
