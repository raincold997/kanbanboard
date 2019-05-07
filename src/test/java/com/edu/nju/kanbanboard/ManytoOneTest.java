package com.edu.nju.kanbanboard;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBCard;
import com.edu.nju.kanbanboard.model.domain.KBColorList;
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
//        KBBoard board = new KBBoard();
//        KBColumn column = new KBColumn();
//        List<KBColumn> columns = new ArrayList<>();
//        column.setColumnName("first");
//        column.setColumnOrder(1);
//        columns.add(column);
//        board.getColumns().addAll(columns);
//        column.setKbBoard(board);
//
//        boardRepository.save(board);
        //columnRepository.save(column);
        Long boardId  = new Long("6");
        KBBoard board = boardRepository.getOne(boardId);
        System.out.println(board.getColumns().size());
    }

    @Test
    public void addColumn(){
        Long boardId  = new Long("6");
        KBBoard board = boardRepository.getOne(boardId);

        KBColumn column = new KBColumn();
        column.setColumnName("test");
        column.setKbBoard(board);
        board.getColumns().add(column);
        boardRepository.save(board);
    }

    @Test
    public void addCard(){
        Long columnId = new Long("5");
        KBColumn column = columnRepository.getOne(columnId);

        KBCard card = new KBCard();
        card.setCardTitle("cardTest");
        card.setCardDescription("kk");
        card.setScale(1);
        card.setKbColumn(column);
        column.getCards().add(card);
        columnRepository.save(column);
    }



}
