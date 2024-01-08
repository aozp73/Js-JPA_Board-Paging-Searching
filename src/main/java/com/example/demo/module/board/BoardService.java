package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public void findAll(BoardListSearch_InDTO boardListSearchInDTO) {

    }
}
