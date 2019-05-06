package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBColorList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorListRepository extends JpaRepository<KBColorList,Long> {
}
