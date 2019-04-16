package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

//    @Override
//    public void create(KBBoard board) {
//        boardRepository.save(board);
//    }

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
}
