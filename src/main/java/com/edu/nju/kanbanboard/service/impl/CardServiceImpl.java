package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBCard;
import com.edu.nju.kanbanboard.repository.CardRepository;
import com.edu.nju.kanbanboard.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void create(KBCard card) {
        card.setColor("Green");
        card.setUpdateDate(new Date());
        cardRepository.save(card);
    }

    @Override
    public void update(KBCard card) {
        card.setUpdateDate(new Date());
        cardRepository.save(card);
    }

    @Override
    public KBCard getById(Long cardId) {
        return cardRepository.getOne(cardId);
    }

    @Override
    public void deleteCard(Long cardId) {
        KBCard card = cardRepository.getOne(cardId);
        card.setKbColumn(null);
        cardRepository.save(card);
        cardRepository.delete(card);

    }


}
