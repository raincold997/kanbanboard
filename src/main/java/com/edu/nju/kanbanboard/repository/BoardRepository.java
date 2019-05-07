package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BoardRepository extends JpaRepository<KBBoard,Long> {
    Set<KBBoard> findAllByOwnerId(Long ownerId);

    @Query("select b.ownerId from KBBoard b where b.boardId = ?1")
    Long findOwnerIdById(Long boardId);
}
