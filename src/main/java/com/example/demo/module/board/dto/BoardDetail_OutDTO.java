package com.example.demo.module.board.dto;

import lombok.*;

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
    private Long commentCount;
    private List<BoardDetailComment_OutDTO> commentDTO;

    public BoardDetail_OutDTO (Long id, Long userid, String title, String content, int views, LocalDateTime createdAt,
                               String username, Long commentCount) {
        this.id = id;
        this.userId = userid;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdAt = createdAt;
        this.username = username;
        this.commentCount = commentCount;
    }
}
