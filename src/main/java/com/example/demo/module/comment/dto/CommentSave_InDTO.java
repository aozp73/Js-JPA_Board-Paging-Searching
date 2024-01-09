package com.example.demo.module.comment.dto;

import com.example.demo.module.board.Board;
import com.example.demo.module.comment.Comment;
import com.example.demo.module.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentSave_InDTO {

    private Long boardId;
    private String content;

    public Comment toEntity(User userEntity, Board boardEntity) {
        return Comment.builder()
                .user(userEntity)
                .board(boardEntity)
                .content(this.content)
                .build();
    }
}
