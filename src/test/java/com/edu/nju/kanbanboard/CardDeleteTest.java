package com.edu.nju.kanbanboard;

import com.edu.nju.kanbanboard.model.domain.KBCard;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.repository.CardRepository;
import com.edu.nju.kanbanboard.repository.ColumnRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CardDeleteTest {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Test
    public void createColumnAndCard(){
//        KBColumn column = new KBColumn();
//        column.setColumnName("test");
//        column.setColumnFlag(1);
//        column.setCardsFlag(1);
//        column.setColumnOrder(1);
//
//        KBCard card1 =new KBCard();
//        card1.setCardTitle("test1");
//        card1.setCardDescription("11");
//        card1.setKbColumn(column);
//        KBCard card2 = new KBCard();
//        card2.setCardTitle("test2");
//        card2.setCardDescription("22");
//        card2.setKbColumn(column);
//        KBCard card3 = new KBCard();
//        card3.setCardTitle("test3");
//        card3.setCardDescription("33");
//        card3.setKbColumn(column);
//
//        column.getCards().add(card1);
//        column.getCards().add(card2);
//        column.getCards().add(card3);
//
//        columnRepository.save(column);
    }

    @Test
    public void deleteOne(){
//        KBColumn column = columnRepository.findByColumnName("test");
//        //KBCard card = cardRepository.findByCardTitle("test1");
//        //System.out.println(card.getCardTitle());
//        //boolean flag = column.getCards().remove(card);
//        //System.out.println(flag);
//        //System.out.println(column.getCards().size());
//
//        //card.setKbColumn(null);
//        //cardRepository.save(card);
//        //cardRepository.delete(card);
//        //columnRepository.save(column);
//        //columnRepository.delete(column);
//        System.out.println(column.getCards().size());
    }

    @Test
    public void addOne(){
//        KBColumn column = columnRepository.findByColumnName("test");
//        KBCard card1 =new KBCard();
//        card1.setCardTitle("test1");
//        card1.setCardDescription("11");
//        card1.setKbColumn(column);
//        column.getCards().add(card1);
//        columnRepository.save(column);
    }
}
