package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="kb_column")
public class KBColumn implements Serializable {

    private static final long serialVersionUID = -1531781243773680003L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @NotEmpty(message = "列名称不能为空")
    private String columnName;

    @Column(nullable = false)
    private int columnOrder;

    @ManyToOne(targetEntity = KBBoard.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private KBBoard kbBoard;

    @OneToMany(mappedBy = "kbColumn",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<KBCard> cards;
}
