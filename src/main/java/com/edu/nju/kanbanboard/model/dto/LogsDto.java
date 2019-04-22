package com.edu.nju.kanbanboard.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LogsDto {
    private String userName;

    private String content;

    private Date logCreated;
}
