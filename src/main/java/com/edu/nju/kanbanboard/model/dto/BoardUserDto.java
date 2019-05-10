package com.edu.nju.kanbanboard.model.dto;

import lombok.Data;

@Data
public class BoardUserDto {
    private Long userId;
    private String userName;
    private String userEmail;
    //0 no 1 yes
    private int isOwner;
}
