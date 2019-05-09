package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBLogs;
import com.edu.nju.kanbanboard.repository.LogsRepository;
import com.edu.nju.kanbanboard.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LogsServiceImpl implements LogsService {
    @Autowired
    private LogsRepository logsRepository;

    @Override
    public Set<KBLogs> getLogsByBoard(Long BoardId) {
        return logsRepository.findAllByBoardId(BoardId);
    }

    @Override
    public void createCardLog(Long userId, Long boardId, String cardTitle) {
        String content = "创建了卡片："+cardTitle;
        saveLog(content,userId,boardId);
    }

    @Override
    public void deleteCardLog(Long userId, Long boardId, String cardTitle) {
        String content = "删除了卡片："+cardTitle;
        saveLog(content,userId,boardId);
    }

    @Override
    public void modifyCardLog(Long userId, Long boardId,String cardTitle) {
        String content = "修改了卡片："+cardTitle;
        saveLog(content,userId,boardId);
    }

    @Override
    public void moveCardLog(Long userId, Long boardId, String cardTitle, String sourceColumn, String targetColumn) {
        String content = "将卡片："+cardTitle+" 从列："+sourceColumn+" 移动到列："+targetColumn;
        saveLog(content,userId,boardId);
    }

    @Override
    public void finishCardLog(Long userId, Long boardId, String cardTitle) {
        String content = cardTitle+" 工作项完成";
        saveLog(content,userId,boardId);
    }

    private void saveLog(String content,Long userId,Long boardId){
        KBLogs logs = new KBLogs();
        logs.setBoardId(boardId);
        logs.setUserId(userId);
        logs.setLogContent(content);
        logsRepository.save(logs);
    }
}
