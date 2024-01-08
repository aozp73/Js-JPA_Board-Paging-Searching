package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListPageInfo_OutDTO;
import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String list(@ModelAttribute("searchInfo") BoardListSearch_InDTO boardListSearchInDTO,
                       @PageableDefault(size = 5) Pageable pageable, Model model) {
        log.debug("GET - 게시글 목록 페이지");

        Page<BoardList_OutDTO> boardList = boardService.findAll(boardListSearchInDTO, pageable);
        BoardListPageInfo_OutDTO pagingInfo = new BoardListPageInfo_OutDTO(boardList);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pagingInfo", pagingInfo);

        return "pages/board/list";
    }
}
