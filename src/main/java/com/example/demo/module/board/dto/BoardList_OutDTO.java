package com.example.demo.module.board.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardList_OutDTO {
    private Long id;
    private Long userId;

    private String title;
    private Integer views;
    private String createdAtFormat;

    // user_tb
    private String username;

    // comment_tb
    private Integer commentCount;
}
