package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import com.example.demo.module.comment.QComment;
import com.example.demo.module.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.demo.module.board.QBoard.board;
import static com.example.demo.module.comment.QComment.comment;
import static com.example.demo.module.user.QUser.user;

@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory query;

    public BoardQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<BoardList_OutDTO> findAllWithUserForList(BoardListSearch_InDTO searchCond) {
        List<BoardList_OutDTO> result = query.select(Projections.constructor(BoardList_OutDTO.class,
                        board.id, board.title, user.username,
                        Expressions.stringTemplate("FORMATDATETIME({0}, {1})", board.createdAt, Expressions.constant("yyyy/MM/dd")),
                        Expressions.numberTemplate(Long.class, "COUNT({0})", comment.id)))

                .from(board)
                .innerJoin(board.user, user)
                .leftJoin(comment).on(board.id.eq(comment.board.id))
                .where(likeTitle(searchCond.getSearchKeyword(), searchCond.getSearchType()),
                        likeAuthor(searchCond.getSearchKeyword(), searchCond.getSearchType()))

                .groupBy(board.id)
                .orderBy(board.createdAt.desc())
                .limit(searchCond.getPageSize())
                .offset(searchCond.getOffset())
                .fetch();

        return result;
    }

    private BooleanExpression likeTitle(String title, String searchType) {
        if ("title".equals(searchType) && StringUtils.hasText(title)) {
            return board.title.like("%" + title + "%");
        }
        return null;
    }

    private BooleanExpression likeAuthor(String username, String searchType) {
        if ("author".equals(searchType) && StringUtils.hasText(username)) {
            return user.username.like("%" + username + "%");
        }
        return null;
    }
}
