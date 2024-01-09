package com.example.demo.module.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdate_InDTO {

    private String content;
    private Long boardId;
    private Long commentId;
}

