package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Transactional(readOnly = true)
    public Page<BoardList_OutDTO> findAll(BoardListSearch_InDTO boardListSearchInDTO, Pageable pageable) {
        return boardQueryRepository.findAllWithUserForList(boardListSearchInDTO, pageable);
    }
}
