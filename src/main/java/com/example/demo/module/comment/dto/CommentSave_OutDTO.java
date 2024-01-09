package com.example.demo.module.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentSave_OutDTO {

    private Long id;
    private Long userId;
    private String username;
    private String content;
    private Boolean editable;
    private Timestamp createdAt;
    private String createdAtFormat;
}
