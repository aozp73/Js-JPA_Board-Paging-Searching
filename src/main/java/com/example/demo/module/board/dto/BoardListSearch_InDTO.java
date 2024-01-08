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

    @Builder.Default
    private Integer page = 1;

    private final Integer pageSize = 5;
    private Integer offset;

    public int getOffset() {
        return (page - 1) * pageSize;
    }
}

