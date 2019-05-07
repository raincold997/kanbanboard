package com.edu.nju.kanbanboard;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardDelete {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void OwnerBoard(){

//        KBBoard board = boardRepository.getOne(Long.parseLong("1"));
//        KBUser user = userRepository.getOne(Long.parseLong("1"));
//        List<KBBoard> newBoards = new ArrayList<>();
//        for(KBBoard sboard:user.getKbBoards()){
//            if(sboard.getBoardId().intValue() != 1){
//                newBoards.add(sboard);
//            }
//        }
//
//        user.setKbBoards(newBoards);
//        userRepository.save(user);
//        boardRepository.delete(board);
    }
}