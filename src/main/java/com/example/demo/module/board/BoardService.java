package com.example.demo.module.board;

import com.example.demo.exception.statuscode.CustomException;
import com.example.demo.exception.statuscode.Exception400;
import com.example.demo.exception.statuscode.Exception500;
import com.example.demo.module.board.dto.*;
import com.example.demo.module.comment.Comment;
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
import java.util.Objects;

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
        List<BoardDetailComment_OutDTO> boardDetailCommentDTO = commentRepository.findAllWithCommentForDetail(boardId);

        return MyDateUtils.boardDetail_Format(boardDetailDTO, boardDetailCommentDTO);
    }

    @Transactional
    public void viewsCount(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException("게시물이 존재하지 않습니다."));

        boardEntity.setViews(boardEntity.getViews() + 1);
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

    @Transactional
    public void delete(Long boardId, Long userId) {
        Board boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException("게시물이 존재하지 않습니다."));

        if (!Objects.equals(boardEntity.getUser().getId(), userId)) {
            throw new Exception400("작성자만 삭제할 수 있습니다.");
        }

        /**
         * 게시글 삭제 시, 댓글 처리 (1:N)
         * - 처 리: 삭제 게시글의 댓글 Board 필드 null -> 게시글 삭제
         * - 이 유: 작성 댓글을 모아서 볼 수 있는 확장성 고려 (Board 필드가 null일 경우 '삭제된 게시물' 표기)
         * - 다른 방법: 1. 게시글, 댓글에 isDeleted 필드 추가 및 상태 관리 (실제 삭제 x)
         *            2. 게시글, 댓글 함께 삭제
         *            3. 별도 백업 DB 구성 후, 고객 센터에서 필요 시 제공
         */
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        for (Comment comment : comments) {
            comment.setBoard(null);
        }

        try {
            boardRepository.deleteById(boardId);
        } catch (Exception exception) {
            throw new Exception500("게시글 삭제에 실패하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public BoardUpdate_OutDTO updateForm(Long boardId, Long userId) {
        Board boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException("게시물이 존재하지 않습니다."));

        if (!Objects.equals(boardEntity.getUser().getId(), userId)) {
            throw new CustomException("작성자만 수정할 수 있습니다.");
        }

        return new BoardUpdate_OutDTO().fromEntity(boardEntity);
    }

    @Transactional
    public void update(BoardUpdate_InDTO boardUpdateInDTO, Long userId) {
        Board boardEntity = boardRepository.findById(boardUpdateInDTO.getId())
                .orElseThrow(() -> new CustomException("게시물이 존재하지 않습니다."));

        if (!Objects.equals(boardEntity.getUser().getId(), userId)) {
            throw new CustomException("작성자만 수정할 수 있습니다.");
        }

        boardUpdateInDTO.toEntity(boardEntity);
    }
}
