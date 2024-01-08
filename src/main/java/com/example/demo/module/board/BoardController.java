package com.example.demo.module.board;

import com.example.demo.config.security.principal.MyUserDetails;
import com.example.demo.exception.ResponseDTO;
import com.example.demo.module.board.dto.BoardListPageInfo_OutDTO;
import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import com.example.demo.module.board.dto.BoardSave_InDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String list(@ModelAttribute("searchInfo") BoardListSearch_InDTO boardListSearchInDTO,
                       @PageableDefault(size = 5) Pageable pageable, Model model) {
        log.debug("GET - 게시글 목록 페이지");

        Page<BoardList_OutDTO> boardList = boardService.findAll(boardListSearchInDTO, pageable);
        BoardListPageInfo_OutDTO pagingInfo = new BoardListPageInfo_OutDTO(boardList);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pagingInfo", pagingInfo);

        return "pages/board/list";
    }

    @GetMapping("/board/{boardId}")
    public String detail(@PathVariable Long boardId, Model model) {
        log.debug("GET - 게시글 상세 페이지");

        boardService.viewsCount(boardId);
        model.addAttribute("boardList", boardService.findById(boardId));

        return "pages/board/detail";
    }

    @GetMapping("/auth/board")
    public String saveForm(@ModelAttribute("boardSaveInDTO") BoardSave_InDTO boardSaveInDTO) {
        log.debug("GET - 게시글 등록 페이지");

        return "pages/board/saveForm";
    }

    @PostMapping("/auth/board")
    public String save(@ModelAttribute("boardSaveInDTO") @Valid BoardSave_InDTO boardSaveInDTO, BindingResult bindingResult,
                       @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("POST - 게시글 등록");

        if (bindingResult.hasErrors()){
            return "pages/board/saveForm";
        }
        Long boardId = boardService.save(boardSaveInDTO, myUserDetails.getUser().getId());

        return "redirect:/board";
    }

    @DeleteMapping("/auth/board/{boardId}")
    public ResponseEntity<?> save(@PathVariable Long boardId, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        log.debug("DELETE - 게시글 삭제");
        boardService.delete(boardId, myUserDetails.getUser().getId());

        return ResponseEntity.ok().body(new ResponseDTO<>());
    }
}
