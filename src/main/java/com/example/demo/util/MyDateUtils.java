package com.example.demo.util;

import com.example.demo.module.board.dto.BoardDetail_OutDTO;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyDateUtils {

    public static BoardDetail_OutDTO detailFormat(BoardDetail_OutDTO boardDetailDTO, List<BoardDetail_OutDTO.CommentDTO> boardDetailCommentDTO) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        // 세부 정보 날짜 포맷팅
        boardDetailDTO.setCreatedAtFormat(dateFormat.format(boardDetailDTO.getCreatedAt()));

        // 댓글 목록 날짜 포맷팅
        boardDetailCommentDTO.forEach(commentDTO ->
                commentDTO.setCreatedAtFormat(dateFormat.format(commentDTO.getCreatedAt()))
        );

        boardDetailDTO.setCommentDTO(boardDetailCommentDTO);

        return boardDetailDTO;
    }
}
