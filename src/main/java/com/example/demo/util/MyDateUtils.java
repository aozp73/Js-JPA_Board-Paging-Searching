package com.example.demo.util;

import com.example.demo.module.board.dto.BoardDetailComment_OutDTO;
import com.example.demo.module.board.dto.BoardDetail_OutDTO;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyDateUtils {

    public static BoardDetail_OutDTO boardDetail_Format(BoardDetail_OutDTO boardDetailDTO,
                                                        List<BoardDetailComment_OutDTO> boardDetailCommentDTO) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        // 세부 정보 날짜 포맷팅
        boardDetailDTO.setCreatedAtFormat(boardDetailDTO.getCreatedAt().format(dateFormat));

        // 댓글 목록 날짜 포맷팅
        boardDetailCommentDTO.forEach(commentDTO ->
                commentDTO.setCreatedAtFormat(commentDTO.getCreatedAt().format(dateFormat))
        );

        boardDetailDTO.setCommentDTO(boardDetailCommentDTO);

        return boardDetailDTO;
    }
}
