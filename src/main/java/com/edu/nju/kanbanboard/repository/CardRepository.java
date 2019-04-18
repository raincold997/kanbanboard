package com.edu.nju.kanbanboard.repository;

import com.edu.nju.kanbanboard.model.domain.KBCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<KBCard,Long> {
    KBCard findByCardTitle(String title);

}
