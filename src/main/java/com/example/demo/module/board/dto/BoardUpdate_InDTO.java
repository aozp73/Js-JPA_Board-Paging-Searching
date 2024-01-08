package com.example.demo.module.board.dto;

import com.example.demo.module.board.Board;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdate_InDTO {

    private Long id;
    private String title;
    private String content;

    public Board toEntity(Board boardEntity) {
        boardEntity.setTitle(this.title);
        boardEntity.setContent(this.content);

        return boardEntity;
    }
}

