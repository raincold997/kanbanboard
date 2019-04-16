package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBLogs;

import java.util.List;

public interface LogsService {
    List<KBLogs> getLogsByBoard(Long BoardId);
}
