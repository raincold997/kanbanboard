package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.model.dto.ColumnInfoDto;
import com.edu.nju.kanbanboard.model.dto.JsonResult;
import com.edu.nju.kanbanboard.model.enums.ResultCodeEnum;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/kanban/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/create/{boardId}")
    @LoggerManager(description = "创建列")
    public JsonResult createColumn(@PathVariable("boardId")Long boardId, @Valid @RequestBody KBColumn column, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        try {
            int newOrder = board.getColumns().size() + 1;
            column.setColumnOrder(newOrder);
            column.setKbBoard(board);
            columnService.create(column);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"成功创建了列");
        }catch (Exception e){
            log.info("创建列时发生错误:"+e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @DeleteMapping("/delete/{columnId}")
    @LoggerManager(description = "删除列")
    public JsonResult deleteColumn(@PathVariable("columnId")Long columnId){
        KBColumn deleteColumn = columnService.getById(columnId);
        if(deleteColumn == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"列不存在");
        }
        try{
            KBBoard board = deleteColumn.getKbBoard();
            Set<KBColumn> columnList = board.getColumns();
            columnList.remove(deleteColumn);
            columnList = columnService.resortColumn(columnList,0-deleteColumn.getColumnOrder());
            board.setColumns(columnList);
            columnService.delete(columnId);
            boardService.update(board);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"删除列成功");
        }catch (Exception e){
            log.info("删除列时发生错误:"+e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @PutMapping("/move/{kanbanId}")
    @LoggerManager(description = "移动列")
    public JsonResult moveColumn(@PathVariable("kanbanId")Long boardId,List<Long> columnList){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        columnService.moveColumns(columnList);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"移动列成功");
    }

    @PutMapping("/modify/{columnId}/{userId}")
    @LoggerManager(description = "修改列基础信息")
    public JsonResult modifyInfo(@PathVariable("columnId")Long columnId,@PathVariable("userId")Long userId,@RequestBody ColumnInfoDto columnInfoDto){
        KBColumn modifyColumn = columnService.getById(columnId);
        if(modifyColumn == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"列不存在");
        }
        try{
            modifyColumn.setColumnName(columnInfoDto.getColumnName());
            modifyColumn.setColumnWIP(columnInfoDto.getColumnWip());
            columnService.update(modifyColumn);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"修改列基础信息成功");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }
}
