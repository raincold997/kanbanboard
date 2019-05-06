package com.edu.nju.kanbanboard.model.domain;

import com.edu.nju.kanbanboard.model.enums.ColorMessageEnum;
import com.edu.nju.kanbanboard.model.enums.ResultCodeEnum;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "kb_colorList")
@Proxy(lazy = false)
public class KBColorList implements Serializable {
    private static final long serialVersionUID = -6729818336779194701L;

    @Id
    private Long boardId;


    private String red;

    private String yellow;

    private String green;

    private String blue;

    private String white;

    private String purple;

    public KBColorList(){
        red = ColorMessageEnum.RED.getDefaultMessage();
        yellow = ColorMessageEnum.YELLOW.getDefaultMessage();
        green = ColorMessageEnum.GREEN.getDefaultMessage();
        blue = ColorMessageEnum.BLUE.getDefaultMessage();
        white = ColorMessageEnum.WHITE.getDefaultMessage();
        purple = ColorMessageEnum.PURPLE.getDefaultMessage();
    }
}
