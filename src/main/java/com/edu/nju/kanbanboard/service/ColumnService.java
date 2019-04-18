package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBColumn;

import java.util.List;
import java.util.Optional;

public interface ColumnService {
    void create(KBColumn column);

    void update(KBColumn column);

    KBColumn getById(Long columnId);

    Optional<KBColumn> delete(Long columnId);

    List<KBColumn> resortColumn(List<KBColumn> columns,int order);

    boolean getColumnAuthority(Long ColumnId);

    void relaseColumnAuthority(Long ColumnId);
}
