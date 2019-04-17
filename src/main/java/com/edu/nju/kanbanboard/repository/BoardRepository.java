package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<KBBoard,Long> {
    List<KBBoard> findAllByOwnerId(Long ownerId);
}
