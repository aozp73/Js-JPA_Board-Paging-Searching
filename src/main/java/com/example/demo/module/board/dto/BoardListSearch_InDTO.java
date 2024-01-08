package com.example.demo.module.board.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListSearch_InDTO {

    private String searchType;
    private String searchKeyword;
}

