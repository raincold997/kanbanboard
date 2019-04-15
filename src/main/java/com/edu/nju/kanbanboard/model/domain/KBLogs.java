package com.edu.nju.kanbanboard.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "kb_logs")
public class KBLogs implements Serializable {
    private static final long serialVersionUID = -5363334538758271329L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Long userId;

    @NotBlank(message = "操作内容不能为空")
    private String logContent;

    @CreatedDate
    @JsonIgnore
    private Date logCreated;
}
