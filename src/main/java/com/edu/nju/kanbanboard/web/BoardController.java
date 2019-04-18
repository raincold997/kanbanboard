package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.model.dto.JsonResult;
import com.edu.nju.kanbanboard.model.enums.ResultCodeEnum;
import com.edu.nju.kanbanboard.service.BoardService;
import com.edu.nju.kanbanboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/kanbans")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;

    @GetMapping("/owner/{ownerId}")
    @LoggerManager(description = "获取OWNER看板")
    public Map<Long,String> getBoardListByOwner(@PathVariable("ownerId") Long ownerId){
        List<KBBoard> boardList ;
        Map<Long,String> ownerBoardMap;
        try {
            boardList = boardService.getByOwnerId(ownerId);
        }catch (Exception e){
            log.debug(e.getMessage());
            return null;
        }
        if(boardList != null && boardList.size() != 0){
            ownerBoardMap = boardList.stream().collect(Collectors.toMap(KBBoard::getBoardId,KBBoard::getBoardName));
            return ownerBoardMap;
        }
        return null;
    }

    @GetMapping("/user/{userId}")
    @LoggerManager(description = "获取USER看板")
    public Map<Long,String> getBoardListByUser(@PathVariable("userId") Long userId) {
        List<KBBoard> ownerBoards = boardService.getByOwnerId(userId);
        List<KBBoard> userBoards = userService.getBoardList(userId);
        if (ownerBoards != null && ownerBoards.size() != 0 && userBoards != null) {
            userBoards.removeAll(ownerBoards);
        }
        if (userBoards != null && userBoards.size() != 0) {
            Map<Long, String> userBoardMap = userBoards.stream().collect(Collectors.toMap(KBBoard::getBoardId, KBBoard::getBoardName));
            return userBoardMap;
        }
        return null;
    }

    @PostMapping("/kanban/{ownerId}")
    @LoggerManager(description = "创建看板")
    public JsonResult createBoard(@Valid KBBoard board, BindingResult bindingResult,@PathVariable("ownerId")Long ownerId){
        if(bindingResult.hasErrors()){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        try{
            KBUser user = userService.findById(ownerId);
            user.getKbBoards().add(board);
            board.setOwnerId(ownerId);
            boardService.create(board);
            userService.update(user);
            return new JsonResult((ResultCodeEnum.SUCCESS.getCode()),"创建成功");
        }catch (Exception e){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @DeleteMapping("/kanban/{ownerId}/{kanbanId}")
    @LoggerManager(description = "删除看板")
    public JsonResult deleteBoard(@PathVariable("ownerId")Long ownerId,@PathVariable("kanbanId")Long kanbanId){
        KBBoard deleteBoard = boardService.findById(kanbanId);
        if(deleteBoard == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"未发现对应看板");
        }
        else if(!deleteBoard.getOwnerId().equals(ownerId)){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户没有对应权限");
        }
        try {
            List<Long> userIdList = deleteBoard.getKbUsers().stream().map(KBUser::getUserId).collect(Collectors.toList());
            //remove board from all the user
            for (Long userId : userIdList) {
                userService.deleteBoard(userId, kanbanId);
            }
            //remove the board
            boardService.remove(kanbanId);
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"成功删除看板");
    }
}
