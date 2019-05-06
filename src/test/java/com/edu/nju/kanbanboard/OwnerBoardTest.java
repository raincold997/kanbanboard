package com.edu.nju.kanbanboard;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.repository.UserRepository;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerBoardTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

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

    @Test
    public void CreateBoard(){
        Long userId = new Long("1");
        KBUser user = userService.findById(userId);

        KBBoard newboard = new KBBoard();
        newboard.setBoardName("testCreate");
        newboard.setOwnerId(userId);
        newboard.getKbUsers().add(user);

        user.getKbBoards().add(newboard);

        boardService.create(newboard);
        userService.update(user);

//        List<KBBoard> boards = user.getKbBoards();
//        System.out.println(boards.size());
    }

    @Test
    public void CreateBoardWithColorList(){
        KBBoard newboard = new KBBoard();
        newboard.setBoardName("testColorList");
        boardService.create(newboard);
    }
}
