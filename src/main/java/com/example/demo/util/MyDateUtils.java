package com.example.demo.util;

import com.example.demo.module.board.dto.BoardDetailComment_OutDTO;
import com.example.demo.module.board.dto.BoardDetail_OutDTO;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyDateUtils {

    /**
     * 호출 시점: 게시글 목록 -> 게시글 상세
     */
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

    /**
     * 호출 시점: 게시글 상세 -> 댓글 작성 -> 성공 시, 댓글 전체 리렌더링
     */
    public static List<BoardDetailComment_OutDTO> boardDetailComment_Format(List<BoardDetailComment_OutDTO> commentList, Long userId) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        commentList.forEach(commentDTO -> {
            // 댓글 목록 날짜 포맷팅
            commentDTO.setCreatedAtFormat(dateFormat.format(commentDTO.getCreatedAt()));

            // Ajax Success 댓글 랜더링 시, 수정/삭제 버튼 구분
            if (commentDTO.getUserId().equals(userId)) {
                commentDTO.setEditable(true);
            } else {
                commentDTO.setEditable(false);
            }
        });

        return commentList;
    }
}
