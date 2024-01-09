package com.example.demo.module.comment;

import com.example.demo.config.security.principal.MyUserDetails;
import com.example.demo.exception.ResponseDTO;
import com.example.demo.module.comment.dto.CommentSave_InDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/auth/comment")
    public ResponseEntity<?> save(@RequestBody CommentSave_InDTO commentSaveInDTO,
                                  @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("POST - 댓글 작성");

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }

}
