package com.edu.nju.kanbanboard.model.dto;

import lombok.Data;

@Data
public class MoveActionDto {
    private Long cardId;

    private Long sourceLaneId;

    private Long targetLaneId;

    private Long useId;

}
