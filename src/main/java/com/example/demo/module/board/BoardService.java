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
    private final BoardQueryRepository boardQueryRepository;

    @Transactional(readOnly = true)
    public List<BoardList_OutDTO> findAll(BoardListSearch_InDTO boardListSearchInDTO) {
        return boardQueryRepository.findAllWithUserForList(boardListSearchInDTO);
    }
}
