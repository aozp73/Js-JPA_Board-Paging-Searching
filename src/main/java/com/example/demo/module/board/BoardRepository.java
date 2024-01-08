package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardDetail_OutDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT new com.example.demo.module.board.dto.BoardDetail_OutDTO(b.id, b.user.id, b.title, b.content, " +
            "b.views, b.createdAt, b.user.username" +
            "COUNT(c.id)) " +

            "FROM Board b JOIN b.user u " +
            "LEFT JOIN Comment c ON b.id = c.board.id " +
            "WHERE b.id = :boardId " +
            "GROUP BY b.id, u.username, b.createdAt")
    BoardDetail_OutDTO findBoardDetailWithUserForDetail(@Param("boardId") Long boardId);
}
