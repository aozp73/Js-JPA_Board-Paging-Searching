package com.example.demo.module.board.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListPageInfo_OutDTO {

    private Integer startPage;
    private Integer endPage;

    public BoardListPageInfo_OutDTO (Page<BoardList_OutDTO> boardList) {
        Integer ButtonCount = 5;
        System.out.println("boardList.getPageable().getPageNumber() = " + boardList.getPageable().getPageNumber());
        Integer tmp = (boardList.getPageable().getPageNumber() + 1) / ButtonCount;
        this.startPage = 1 + (tmp * ButtonCount);
        this.endPage = 5 + (tmp * ButtonCount);
    }
}
