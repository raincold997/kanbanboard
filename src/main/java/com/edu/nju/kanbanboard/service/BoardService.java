package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBBoard;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    void create(KBBoard board);

    void update(KBBoard board);

    KBBoard findById(Long boardId);

    Optional<KBBoard> remove(Long commentId);

    List<KBBoard> getByOwnerId(Long ownerId);
}
