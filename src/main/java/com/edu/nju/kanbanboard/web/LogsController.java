package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBLogs;
import com.edu.nju.kanbanboard.model.dto.LogsDto;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.service.LogsService;
import com.edu.nju.kanbanboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kanbans/Logs")
public class LogsController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private UserService userService;


    @GetMapping("/get/{kanbanId}")
    @LoggerManager(description = "获取看板操作记录")
    public List<LogsDto> getByBoard(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return null;
        }
        List<KBLogs> logs = logsService.getLogsByBoard(boardId);
        List<LogsDto> logsDtoList = new ArrayList<>();
        if(logs.size() >0){
            for(KBLogs log:logs){
                LogsDto logsDto = new LogsDto();
                logsDto.setContent(log.getLogContent());
                logsDto.setUserName(userService.getNameById(log.getUserId()));
                logsDto.setLogCreated(log.getLogCreated());
                logsDtoList.add(logsDto);
            }
        }
        return logsDtoList;
    }
}
