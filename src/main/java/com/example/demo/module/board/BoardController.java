package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String list(@ModelAttribute("searchInfo") BoardListSearch_InDTO boardListSearchInDTO, Model model) {
        log.debug("GET - 게시글 목록 페이지");

        model.addAttribute("boardList", boardService.findAll(boardListSearchInDTO));

        return "pages/board/list";
    }
}
