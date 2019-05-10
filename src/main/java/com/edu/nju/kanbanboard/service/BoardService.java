package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBColorList;
import com.edu.nju.kanbanboard.model.dto.BoardUserDto;
import com.edu.nju.kanbanboard.model.dto.StatisticsInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BoardService {

    void create(KBBoard board);

    void update(KBBoard board);

    KBBoard findById(Long boardId);

    Optional<KBBoard> remove(Long commentId);

    Set<KBBoard> getByOwnerId(Long ownerId);

    KBColorList getColorListById(Long boardId);

    Long getOwnerId(Long boardId);

    void updateColorList(KBColorList colorList);

    String getLeadTime(KBBoard board);

    List<String> getThroughputOneWeek(KBBoard board);

    List<StatisticsInfoDto> getStatisticsInfo(KBBoard board);

    List<BoardUserDto> getUserList(KBBoard board);
}
