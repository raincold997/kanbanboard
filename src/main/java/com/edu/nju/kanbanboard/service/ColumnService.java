package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBColumn;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ColumnService {
    void create(KBColumn column);

    void update(KBColumn column);

    KBColumn getById(Long columnId);

    void delete(Long columnId);

    Set<KBColumn> resortColumn(Set<KBColumn> columns, int order);

    void moveColumns(List<Long> orderList);

    boolean getColumnAuthority(Long ColumnId);

    void releaseColumnAuthority(Long ColumnId);


}
