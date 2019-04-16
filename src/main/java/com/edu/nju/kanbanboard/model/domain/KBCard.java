package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "kb_card")
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

    private Long creatorId;
}
