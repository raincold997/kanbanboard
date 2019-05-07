package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "kb_board")
@Proxy(lazy = false)
public class KBBoard implements Serializable {
    private static final long serialVersionUID = -111835654956927440L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @OneToMany(mappedBy = "kbBoard",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<KBColumn> columns = new HashSet<>();

    @ManyToMany( mappedBy = "kbBoards")
    @JsonIgnore
    private Set<KBUser> kbUsers = new HashSet<>();

    private Long ownerId;

    @NotBlank(message = "看板名称不能为空")
    private String boardName;

    @Override
    public boolean equals(Object x){
        if(this == x)return true;
        if(x == null)return false;
        if(this.getClass() != x.getClass())return false;
        KBBoard that = (KBBoard)x;

        return this.boardId.equals(that.boardId);
    }
}
