package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    //卡片颜色
    private String color;

    //创建者ID
    private Long creatorId;

    //创建者姓名，用于卡片展示
    private String creatorName;

    //创建日期
    private Date createDate;

    //结束日期
    private Date finishDate;

    //前置时间，单位为天
    private int leadTime;

    //更新日期
    private Date updateDate;

    //是否阻塞,更新日期超过2日没有被完成
    private int isBlocked;

    //工作截至日期
    private Date deadline;

    //工作规模,1~9
    @Max(9)
    @Min(1)
    private int scale;

    //工作进度，0~9
    @Max(9)
    @Min(0)
    private int rate;

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
