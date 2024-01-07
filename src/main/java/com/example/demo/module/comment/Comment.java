package com.example.demo.module.comment;

import com.example.demo.module.board.Board;
import com.example.demo.module.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_tb")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}