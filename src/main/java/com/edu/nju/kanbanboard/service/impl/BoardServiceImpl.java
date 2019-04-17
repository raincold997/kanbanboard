package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public void create(KBBoard board) {
        List<KBColumn> columns = board.getColumns();
        int columnOrder = 1;
        for(KBColumn column:columns){
            column.setKbBoard(board);
            column.setColumnFlag(1);
            column.setCardsFlag(1);
            column.setColumnOrder(columnOrder);
            columnOrder++;
        }
        boardRepository.save(board);
    }

    @Override
    public void update(KBBoard board) {
        boardRepository.save(board);
    }



    @Override
    public KBBoard findById(Long boardId) {
        return null;
    }

    @Override
    public Optional<KBBoard> remove(Long boardId) {
        final Optional<KBBoard> board = boardRepository.findById(boardId);
        boardRepository.delete(board.orElse(null));
        return board;
    }

    @Override
    public List<KBBoard> getByOwnerId(Long ownerId) {
        return boardRepository.findAllByOwnerId(ownerId);
    }
}
