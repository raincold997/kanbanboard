package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.repository.UserRepository;
import com.edu.nju.kanbanboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(KBUser user) {
        userRepository.save(user);
    }

    @Override
    public KBUser findById(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public KBUser findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public KBUser findByName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public void update(KBUser user) {
        userRepository.save(user);
    }

    @Override
    public List<KBBoard> getBoardList(Long userId) {
        KBUser user = userRepository.getOne(userId);
        if(user != null){
            return user.getKbBoards();
        }
        return null;
    }
}
