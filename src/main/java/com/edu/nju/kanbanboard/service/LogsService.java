package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBLogs;

import java.util.List;

public interface LogsService {
    List<KBLogs> getLogsByBoard(Long BoardId);

    void createCardLog(Long userId,Long boardId,String cardTitle);

    void deleteCardLog(Long userId,Long boardId,String cardTitle);

    void moveCardLog(Long userId,Long boardId,String cardTitle,String sourceColumn,String targetColumn);
}
