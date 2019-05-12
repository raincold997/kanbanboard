package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBBoard;
import com.edu.nju.kanbanboard.model.domain.KBCard;
import com.edu.nju.kanbanboard.model.domain.KBColorList;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.model.dto.BoardUserDto;
import com.edu.nju.kanbanboard.model.dto.JsonResult;
import com.edu.nju.kanbanboard.model.dto.StatisticsInfoDto;
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
import java.util.Set;
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
        Set<KBBoard> boardList ;
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

    @GetMapping("/user/boardList/{userId}")
    @LoggerManager(description = "获取USER看板")
    public Map<Long,String> getBoardListByUser(@PathVariable("userId") Long userId) {
        //Set<KBBoard> ownerBoards = boardService.getByOwnerId(userId);
        Set<KBBoard> userBoards = userService.getBoardList(userId);
//        if (ownerBoards != null && ownerBoards.size() != 0 && userBoards != null) {
//            userBoards.removeAll(ownerBoards);
//        }
        if (userBoards != null && userBoards.size() != 0) {
            Map<Long, String> userBoardMap = userBoards.stream().collect(Collectors.toMap(KBBoard::getBoardId, KBBoard::getBoardName));
            return userBoardMap;
        }
        return null;
    }

    @GetMapping("/{kanbanId}")
    @LoggerManager(description = "获取单个看板")
    public JsonResult getBoard(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"获取看板成功",board);
    }

    @PostMapping("/kanban/{ownerId}")
    @LoggerManager(description = "创建看板")
    public JsonResult createBoard(@RequestBody @Valid KBBoard board, BindingResult bindingResult,@PathVariable("ownerId")Long ownerId){
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

    @PutMapping("/board/name/{kanbanId}/{userId}")
    @LoggerManager(description = "修改看板名称")
    public JsonResult renameBoard(@PathVariable("kanbanId")Long boardId,@PathVariable("userId")Long ownerId,@RequestBody String newName){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"未发现对应看板");
        }
        if(!board.getOwnerId().equals(ownerId)){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户没有对应权限");
        }
        try{
            board.setBoardName(newName);
            boardService.update(board);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"修改看板名称成功");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @PutMapping("/board/color/{kanbanId}/{userId}")
    @LoggerManager(description = "修改颜色列表")
    public JsonResult modifyColorList(@PathVariable("kanbanId")Long boardId, @PathVariable("userId")Long ownerId, @RequestBody KBColorList colorList){
        KBColorList modifyColorList = boardService.getColorListById(boardId);
        if(modifyColorList ==  null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        if(!ownerId.equals(boardService.getOwnerId(boardId))){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户没有对应权限");
        }
        try {
            colorList.setBoardId(boardId);
            boardService.updateColorList(colorList);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"修改颜色列表成功");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @PostMapping("/user/add/{kanbanId}/{userId}")
    @LoggerManager(description = "看板添加用户")
    public JsonResult addUser(@PathVariable("kanbanId")Long boardId,@PathVariable("userId")Long ownerId,@RequestParam String targetEmail){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        if(!ownerId.equals(board.getOwnerId())){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户没有权限");
        }
        try{
            KBUser user = userService.findByEmail(targetEmail);
            user.getKbBoards().add(board);
            board.getKbUsers().add(user);
            boardService.update(board);
            userService.update(user);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"添加用户成功");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @DeleteMapping("/user/delete/{kanbanId}/{userId}")
    @LoggerManager(description = "看板删除用户")
    public JsonResult deleteUser(@PathVariable("kanbanId")Long boardId,@PathVariable("userId")Long ownerId,@RequestParam Long targetId){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"对应看板不存在");
        }
        if(!ownerId.equals(board.getOwnerId())){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户没有权限");
        }
        try{
            KBUser user = userService.findById(targetId);
            user.getKbBoards().remove(board);
            userService.update(user);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"删除用户成功");
        }catch (Exception e){
            log.debug(e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了错误");
        }
    }

    @GetMapping("/user/userList/{kanbanId}")
    @LoggerManager(description = "获取看板用户列表")
    public JsonResult getUserList(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"对应看板不存在");
        }
        List<BoardUserDto> boardUserDtos = boardService.getUserList(board);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"获取成功",boardUserDtos);
    }

    @GetMapping("/leadTime/{kanbanId}")
    @LoggerManager(description = "获取平均前置时间")
    public JsonResult getLeadTime(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        String leadTimeMessage = boardService.getLeadTime(board);
        if(leadTimeMessage.equals("-1")){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"错误，无结束列");
        }
        if(leadTimeMessage.equals("-2")){
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"暂无已完成工作项","暂无已完成工作项");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"获取成功",leadTimeMessage);
    }

    @GetMapping("/throughput/{kanbanId}")
    @LoggerManager(description = "获取一周吞吐量")
    public JsonResult getThroughput(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board ==  null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        List<String> throughput = boardService.getThroughputOneWeek(board);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"获取吞吐量成功",throughput);
    }

    @GetMapping("/statisticsInfo/{kanbanId}")
    @LoggerManager(description = "获取看板统计信息")
    public JsonResult getStaticsInfo(@PathVariable("kanbanId")Long boardId){
        KBBoard board = boardService.findById(boardId);
        if(board ==  null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"看板不存在");
        }
        List<StatisticsInfoDto> statisticsInfos = boardService.getStatisticsInfo(board);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"获取成功",statisticsInfos);
    }
}
