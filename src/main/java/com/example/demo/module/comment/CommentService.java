package com.example.demo.module.comment;

import com.example.demo.exception.statuscode.CustomException;
import com.example.demo.exception.statuscode.Exception500;
import com.example.demo.module.board.Board;
import com.example.demo.module.board.BoardRepository;
import com.example.demo.module.board.dto.BoardDetailComment_OutDTO;
import com.example.demo.module.comment.dto.CommentSave_InDTO;
import com.example.demo.module.comment.dto.CommentSave_OutDTO;
import com.example.demo.module.user.User;
import com.example.demo.module.user.UserRepository;
import com.example.demo.util.MyDateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void save(CommentSave_InDTO commentSaveInDTO, Long userId) {
        Board boardEntity = boardRepository.findById(commentSaveInDTO.getBoardId())
                .orElseThrow(() -> new CustomException("게시물이 존재하지 않습니다."));

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("회원 정보를 확인해주세요."));


        try {
            commentRepository.save(commentSaveInDTO.toEntity(boardEntity, userEntity));
        } catch (Exception exception) {
            throw new Exception500("댓글 저장에 실패하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<BoardDetailComment_OutDTO> findAllForSave(Long boardId, Long userId) {
        List<BoardDetailComment_OutDTO> commentList;

        try {
            commentList = commentRepository.findAllWithCommentForDetail(boardId);
        } catch (Exception exception) {
            throw new Exception500("댓글 저장에 실패하였습니다."); // 저장 로직에 엮임
        }

        return MyDateUtils.boardDetailComment_Format(commentList, userId);
    }
}
