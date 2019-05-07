package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBColorList;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.repository.ColorListRepository;
import com.edu.nju.kanbanboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColorListRepository colorListRepository;

    @Override
    public void create(KBBoard board) {
        Set<KBColumn> columns = board.getColumns();
        int columnOrder = 1;
        if(columns.size()>0) {
            for (KBColumn column : columns) {
                column.setKbBoard(board);
                column.setColumnFlag(1);
                column.setCardsFlag(1);
                column.setColumnOrder(columnOrder);
                columnOrder++;
            }
        }
        boardRepository.save(board);
        KBColorList colorList = new KBColorList();
        colorList.setBoardId(board.getBoardId());
        colorListRepository.save(colorList);
    }

    @Override
    public void update(KBBoard board) {
        boardRepository.save(board);
    }



    @Override
    public KBBoard findById(Long boardId) {
        return boardRepository.getOne(boardId);
    }

    @Override
    public Optional<KBBoard> remove(Long boardId) {
        final Optional<KBBoard> board = boardRepository.findById(boardId);
        boardRepository.delete(board.orElse(null));
        return board;
    }

    @Override
    public Set<KBBoard> getByOwnerId(Long ownerId) {
        return boardRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public KBColorList getColorListById(Long boardId) {
        return colorListRepository.getOne(boardId);
    }

    @Override
    public Long getOwnerId(Long boardId) {
        return boardRepository.findOwnerIdById(boardId);
    }

    @Override
    public void updateColorList(KBColorList colorList) {
        colorListRepository.save(colorList);
    }
}
