package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="kb_column")
@Proxy(lazy = false)
public class KBColumn implements Serializable {

    private static final long serialVersionUID = -1531781243773680003L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @NotEmpty(message = "列名称不能为空")
    private String columnName;

    @JsonIgnore
    @Column(nullable = false)
    private int columnOrder;

    private int columnWIP;

    @ManyToOne(targetEntity = KBBoard.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private KBBoard kbBoard;

    @OneToMany(mappedBy = "kbColumn",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<KBCard> cards = new ArrayList<>();

    @JsonIgnore
    private int columnFlag;

    @JsonIgnore
    private int cardsFlag;

    @Override
    public boolean equals(Object x){
        if(this == x)return true;
        if(x == null)return false;
        if(this.getClass() != x.getClass())return false;
        KBColumn that = (KBColumn)x;

        return this.columnId.equals(that.columnId);
    }
}
