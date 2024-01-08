package com.example.demo.module.board;

import com.example.demo.exception.statuscode.CustomException;
import com.example.demo.module.board.dto.BoardDetail_OutDTO;
import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import com.example.demo.module.board.dto.BoardSave_InDTO;
import com.example.demo.module.comment.CommentRepository;
import com.example.demo.module.user.User;
import com.example.demo.module.user.UserRepository;
import com.example.demo.util.MyDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public BoardDetail_OutDTO findById(Long boardId) {
        BoardDetail_OutDTO boardDetailDTO = boardRepository.findBoardDetailWithUserForDetail(boardId);
        List<BoardDetail_OutDTO.CommentDTO> boardDetailCommentDTO = commentRepository.findAllWithCommentForDetail(boardId);

        return MyDateUtils.detailFormat(boardDetailDTO, boardDetailCommentDTO);
    }

    @Transactional(readOnly = true)
    public Page<BoardList_OutDTO> findAll(BoardListSearch_InDTO boardListSearchInDTO, Pageable pageable) {
        return boardQueryRepository.findAllWithUserForList(boardListSearchInDTO, pageable);
    }

    @Transactional
    public Long save(BoardSave_InDTO boardSaveInDTO, Long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("회원 정보를 확인해주세요."));

        Board board = boardSaveInDTO.toEntity(userEntity);
        try {
            boardRepository.save(board);
        } catch (Exception exception) {
            throw new CustomException("게시글 저장에 실패하였습니다.");
        }

        return board.getId();
    }
}
