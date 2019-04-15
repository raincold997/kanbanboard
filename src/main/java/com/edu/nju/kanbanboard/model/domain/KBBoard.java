package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "kb_board")
public class KBBoard implements Serializable {
    private static final long serialVersionUID = -111835654956927440L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @OneToMany(mappedBy = "kbBoard",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<KBColumn> columns = new ArrayList<>();

    @ManyToMany( mappedBy = "kbBoards")
    @JsonIgnore
    private List<KBUser> kbUsers = new ArrayList<>();

    @NotBlank(message = "看板名称不能为空")
    private String boardName;
}
