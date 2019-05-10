package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.*;
import com.edu.nju.kanbanboard.model.dto.BoardUserDto;
import com.edu.nju.kanbanboard.model.dto.StatisticsInfoDto;
import com.edu.nju.kanbanboard.repository.BoardRepository;
import com.edu.nju.kanbanboard.repository.ColorListRepository;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.utils.Arith;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColorListRepository colorListRepository;

    @Override
    public void create(KBBoard board) {
        Set<KBColumn> columns = board.getColumns();
        int columnOrder = 1;
        if(columns.size()>0) {
            for (KBColumn column : columns) {
                column.setKbBoard(board);
                column.setColumnFlag(1);
                column.setCardsFlag(1);
                column.setColumnOrder(columnOrder);
                columnOrder++;
            }
        }
        boardRepository.save(board);
        KBColorList colorList = new KBColorList();
        colorList.setBoardId(board.getBoardId());
        colorListRepository.save(colorList);
    }

    @Override
    public void update(KBBoard board) {
        boardRepository.save(board);
    }



    @Override
    public KBBoard findById(Long boardId) {
        return boardRepository.getOne(boardId);
    }

    @Override
    public Optional<KBBoard> remove(Long boardId) {
        final Optional<KBBoard> board = boardRepository.findById(boardId);
        boardRepository.delete(board.orElse(null));
        return board;
    }

    @Override
    public Set<KBBoard> getByOwnerId(Long ownerId) {
        return boardRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public KBColorList getColorListById(Long boardId) {
        return colorListRepository.getOne(boardId);
    }

    @Override
    public Long getOwnerId(Long boardId) {
        return boardRepository.findOwnerIdById(boardId);
    }

    @Override
    public void updateColorList(KBColorList colorList) {
        colorListRepository.save(colorList);
    }

    @Override
    public String getLeadTime(KBBoard board) {
        KBColumn finishColumn = getFinishColumn(board);
        if(finishColumn == null){
            return "-1";
        }
        Set<KBCard> cards = finishColumn.getCards();
        double leadTime = 0.0;
        if(cards.size() > 0){
            for(KBCard card:cards){
                leadTime = Arith.add(leadTime,(double)card.getLeadTime());
            }
            leadTime = Arith.div(leadTime,(double)cards.size());
            return leadTime+"天";
        }
        return "-2";
    }

    @Override
    public List<String> getThroughputOneWeek(KBBoard board) {
        KBColumn finishColumn = getFinishColumn(board);
        List<String> throughPut = new ArrayList<>();
        if(finishColumn != null){
            Set<KBCard> cards = finishColumn.getCards();
            if(cards.size()>0){
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE,-7);
                Date lastWeek = c.getTime();
                for(KBCard card:cards){
                    if(card.getFinishDate().after(lastWeek)){
                        throughPut.add(card.getCardTitle());
                    }
                }
            }
        }
        return throughPut;
    }

    @Override
    public List<StatisticsInfoDto> getStatisticsInfo(KBBoard board) {
        KBColumn finishColumn = getFinishColumn(board);
        List<StatisticsInfoDto> sids = new ArrayList<>();
        if(finishColumn != null){
            Set<KBCard> cards = finishColumn.getCards();
            for(KBCard card:cards){
                if(card.getFinishDate() != null){
                    StatisticsInfoDto sid = new StatisticsInfoDto();
                    sid.setCardTitle(card.getCardTitle());
                    sid.setFinishDate(card.getFinishDate());
                    sid.setLeadTime(card.getLeadTime());
                    sids.add(sid);
                }else{
                    log.debug("wrong card,check id: "+card.getCardId());
                }
            }
        }
        return sids;
    }

    @Override
    public List<BoardUserDto> getUserList(KBBoard board) {
        List<BoardUserDto> boardUsers = new ArrayList<>();
        for(KBUser user:board.getKbUsers()){
            BoardUserDto boardUser = new BoardUserDto();
            boardUser.setUserId(user.getUserId());
            boardUser.setUserName(user.getUserName());
            boardUser.setUserEmail(user.getUserEmail());
            if(board.getOwnerId().equals(user.getUserId())){
                boardUser.setIsOwner(1);
            }else {
                boardUser.setIsOwner(0);
            }
            boardUsers.add(boardUser);
        }
        return boardUsers;
    }

    private KBColumn getFinishColumn(KBBoard board){
        Set<KBColumn> columns = board.getColumns();
        int size = columns.size();
        if(size != 0) {
            for (KBColumn column : columns) {
                if (column.getColumnOrder() == size) {
                    return column;
                }
            }
        }
        return null;
    }
}
