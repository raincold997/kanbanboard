package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBCard;

public interface CardService {
    void create(KBCard card);

    void update(KBCard card);

}
