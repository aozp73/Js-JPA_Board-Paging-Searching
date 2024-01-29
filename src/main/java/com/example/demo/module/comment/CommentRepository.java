package com.example.demo.module.comment;

import com.example.demo.module.board.dto.BoardDetailComment_OutDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new com.example.demo.module.board.dto.BoardDetailComment_OutDTO(c.id, c.user.id, c.user.username, c.content, " +
            "c.createdAt) " +
            "FROM Comment c " +
            "WHERE c.board.id = :boardId")
    List<BoardDetailComment_OutDTO> findAllWithCommentForDetail(@Param("boardId") Long boardId);

    List<Comment> findByBoardId(Long boardId);
}
