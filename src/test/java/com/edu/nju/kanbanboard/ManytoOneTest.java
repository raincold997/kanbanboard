package com.edu.nju.kanbanboard;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.repository.ColumnRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManytoOneTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Test
    public void addBoard(){
        KBBoard board = new KBBoard();
        KBColumn column = new KBColumn();
        List<KBColumn> columns = new ArrayList<>();
        column.setColumnName("first");
        column.setColumnOrder(1);
        columns.add(column);
        board.getColumns().addAll(columns);
        column.setKbBoard(board);

        boardRepository.save(board);
        //columnRepository.save(column);

    }


}
