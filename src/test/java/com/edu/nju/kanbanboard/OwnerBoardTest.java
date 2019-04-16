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

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerBoardTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void OwnerBoard(){
        KBBoard board = new KBBoard();
        KBUser user = new KBUser();

        user.setUserName("syh");
        user.setUserPass("123");
        user.setUserEmail("108822355@qq.com");

        userRepository.save(user);

        Long userId = user.getUserId();

        board.setOwnerId(userId);
        board.getKbUsers().add(user);
        board.setBoardName("testBoard");

        user.getKbBoards().add(board);

        boardRepository.save(board);
        userRepository.save(user);
    }
}
