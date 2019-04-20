package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBCard;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.model.dto.JsonResult;
import com.edu.nju.kanbanboard.model.dto.MoveActionDto;
import com.edu.nju.kanbanboard.model.enums.ResultCodeEnum;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.service.CardService;
import com.edu.nju.kanbanboard.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kanban/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/create/{laneId}")
    @LoggerManager(description = "新建卡片")
    public JsonResult createCard(@PathVariable("laneId")Long columnId, @Valid KBCard card, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        try{
            KBColumn column = columnService.getById(columnId);
            card.setKbColumn(column);
            cardService.create(card);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"添加卡片成功");
        }catch (Exception e){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @DeleteMapping("/delete/{cardId}")
    @LoggerManager(description = "删除卡片")
    public JsonResult deleteCard(@PathVariable("cardId")Long cardId){
        KBCard deleteCard = cardService.getById(cardId);
        if(deleteCard == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"未发现对应卡片");
        }
        try{
            cardService.deleteCard(cardId);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"成功删除");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @PutMapping("/move/{kanbanId}")
    @LoggerManager(description = "移动卡片")
    public JsonResult moveCard(@PathVariable("kanbanId") Long boardId, MoveActionDto moveActionDto){
        KBBoard board = boardService.findById(boardId);
        KBColumn sourceColumn = columnService.getById(moveActionDto.getSourceLaneId());
        KBColumn targetColumn = columnService.getById(moveActionDto.getTargetLaneId());
        KBCard moveCard = cardService.getById(moveActionDto.getCardId());
        if(board != null && sourceColumn!=null && targetColumn != null){
            List<KBColumn> columns = board.getColumns();
            if(columns.contains(sourceColumn)&&columns.contains(targetColumn)){
                if(sourceColumn.getCards().contains(moveCard)){
                    moveCard.setKbColumn(targetColumn);
                    cardService.update(moveCard);
                    return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"移动成功");
                }
                return new JsonResult(ResultCodeEnum.FAIL.getCode(),"源列中不存在这张卡片");
            }
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"源列或者目标列不在要求的看板中");
        }
        return new JsonResult(ResultCodeEnum.FAIL.getCode(),"指定的看板或者列不存在");
    }

}