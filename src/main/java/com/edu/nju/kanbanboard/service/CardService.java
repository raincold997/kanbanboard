package com.edu.nju.kanbanboard.service;

import com.edu.nju.kanbanboard.model.domain.KBCard;

public interface CardService {
    void create(KBCard card);

    void update(KBCard card);

    KBCard getById(Long cardId);

    void deleteCard(Long cardId);

    void moidfyCard(KBCard cardInfo,KBCard modifyCard);
}
