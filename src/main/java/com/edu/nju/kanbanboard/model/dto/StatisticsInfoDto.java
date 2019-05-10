package com.edu.nju.kanbanboard.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StatisticsInfoDto {
    private String cardTitle;
    private Date finishDate;
    private int leadTime;

}
