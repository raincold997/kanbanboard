package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepository extends JpaRepository<KBLogs,Long> {
    List<KBLogs> findAllByBoardId(Long boardId);
}
