package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "kb_card")
@Proxy(lazy = false)
public class KBCard implements Serializable {

    private static final long serialVersionUID = -6993212498916368968L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @NotBlank(message = "卡片标题不能为空")
    private String cardTitle;

    @NotBlank(message = "卡片内容不能为空")
    private String cardDescription;

    @ManyToOne(targetEntity = KBColumn.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "column_id")
    @JsonIgnore
    private KBColumn kbColumn;

    private String color;

    private Long creatorId;

    private Date updateDate;

    @Override
    public boolean equals(Object x){
        if(this == x)return true;
        if(x == null)return false;
        if(this.getClass() != x.getClass()) return false;
        KBCard that = (KBCard)x;

        if(!this.cardId.equals(that.cardId)) return false;
        return true;
    }


}
