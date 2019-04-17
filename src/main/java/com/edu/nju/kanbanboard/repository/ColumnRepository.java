package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<KBColumn,Long> {
    KBColumn findByColumnName(String name);
}
