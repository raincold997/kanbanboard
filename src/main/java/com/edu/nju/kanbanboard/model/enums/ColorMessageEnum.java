package com.edu.nju.kanbanboard.model.enums;

import org.springframework.stereotype.Component;

public enum ColorMessageEnum {
    RED("被阻塞的工作"),

    YELLOW("需求工作"),

    GREEN("技术工作"),

    BLUE("自定义工作"),

    WHITE("自定义工作"),

    PURPLE("自定义工作");


    String DefaultMessage;

    ColorMessageEnum(String message){this.DefaultMessage = message;}

    public String getDefaultMessage(){return DefaultMessage;}
}
