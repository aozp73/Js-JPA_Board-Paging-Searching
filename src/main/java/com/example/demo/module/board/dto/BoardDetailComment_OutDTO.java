package com.example.demo.module.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDetailComment_OutDTO {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private Boolean editable;
    private LocalDateTime createdAt;
    private String createdAtFormat;

    public BoardDetailComment_OutDTO(Long id, Long userId, String username, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }
}
