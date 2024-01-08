package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardDetail_OutDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT new com.example.demo.module.board.dto.BoardDetail_OutDTO(" +
            "b.id, b.user.id, b.title, b.content, b.views, b.createdAt, b.user.username, " +
            "(SELECT COUNT(c.id) FROM Comment c WHERE c.board.id = b.id)) " +
            "FROM Board b " +
            "WHERE b.id = :boardId")
    BoardDetail_OutDTO findBoardDetailWithUserForDetail(@Param("boardId") Long boardId);
}
