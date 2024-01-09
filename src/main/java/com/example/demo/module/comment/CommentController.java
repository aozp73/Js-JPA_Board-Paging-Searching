package com.example.demo.module.comment;

import com.example.demo.config.security.principal.MyUserDetails;
import com.example.demo.exception.ResponseDTO;
import com.example.demo.module.board.dto.BoardDetailComment_OutDTO;
import com.example.demo.module.comment.dto.CommentSave_InDTO;
import com.example.demo.module.comment.dto.CommentSave_OutDTO;
import com.example.demo.module.comment.dto.CommentUpdate_InDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/auth/comment")
    public ResponseEntity<?> save(@RequestBody CommentSave_InDTO commentSaveInDTO,
                                  @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("POST - 댓글 작성");
        commentService.save(commentSaveInDTO, myUserDetails.getUser().getId());

        // 전체 댓글 리 렌더링
        List<BoardDetailComment_OutDTO> commentList
                = commentService.findAllForSave(commentSaveInDTO.getBoardId(), myUserDetails.getUser().getId());


        return ResponseEntity.ok().body(new ResponseDTO<>().data(commentList));
    }

    @DeleteMapping("/auth/comment/{boardId}/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, @PathVariable Long boardId,
                                    @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("DELETE - 댓글 삭제");
        commentService.delete(commentId, myUserDetails.getUser().getId());

        // 전체 댓글 리 렌더링
        List<BoardDetailComment_OutDTO> commentList
                = commentService.findAllForSave(boardId, myUserDetails.getUser().getId());

        return ResponseEntity.ok().body(new ResponseDTO<>().data(commentList));
    }


    @PutMapping("/auth/comment/{boardId}/{commentId}")
    public ResponseEntity<?> update(@RequestBody CommentUpdate_InDTO commentUpdateInDTO,
                                    @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("PUT - 댓글 수정");
        commentService.update(commentUpdateInDTO, myUserDetails.getUser().getId());

        // 전체 댓글 리 렌더링
        List<BoardDetailComment_OutDTO> commentList
                = commentService.findAllForSave(commentUpdateInDTO.getBoardId(), myUserDetails.getUser().getId());

        return ResponseEntity.ok().body(new ResponseDTO<>().data(commentList));
    }
}
