package com.example.demo.module.board.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetail_OutDTO {
    private Long id;
    private Long userId;

    private String title;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private String createdAtFormat;

    // user_tb
    private String username;
    // comment_tb
    private Integer commentCount;
    private List<CommentDTO> commentDTO;

    public BoardDetail_OutDTO (Long id, Long userid, String title, String content, Integer views, LocalDateTime createdAtFormat,
                               String username, Integer commentCount) {
        this.id = id;
        this.userId = userid;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdAt = createdAtFormat;
        this.username = username;
        this.commentCount = commentCount;
    }

    @Getter
    @Setter
    @ToString
    public static class CommentDTO {
        private Long id;
        private Long userId;
        private String username;
        private String content;
        private Boolean editable;
        private Timestamp createdAt;
        private String createdAtFormat;
    }
}
