package com.edu.nju.kanbanboard.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MoveActionDto {
    private Long cardId;

    private Long sourceLaneId;

    private Long targetLaneId;

    private Long userId;

    private Date version;

    //0 unfinished    1 finished
    private int isFinish;

}
