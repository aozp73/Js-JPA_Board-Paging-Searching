package com.example.demo.module.board;

import com.example.demo.module.board.dto.BoardListSearch_InDTO;
import com.example.demo.module.board.dto.BoardList_OutDTO;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public Page<BoardList_OutDTO> findAllWithUserForList(BoardListSearch_InDTO searchCond, Pageable pageable) {

        JPAQuery<BoardList_OutDTO> jpaQuery = query.select(Projections.constructor(BoardList_OutDTO.class,
                        board.id, board.user.id, board.title, board.views, user.username,
                        Expressions.stringTemplate("CONCAT(YEAR({0}), '/', LPAD(MONTH({0}), 2, '0'), '/', LPAD(DAY({0}), 2, '0'))", board.createdAt),
                        Expressions.numberTemplate(Integer.class, "COUNT({0})", comment.id)))

                .from(board)
                .innerJoin(board.user, user)
                .leftJoin(comment).on(board.id.eq(comment.board.id))
                .where(likeTitle(searchCond.getSearchKeyword(), searchCond.getSearchType()),
                        likeAuthor(searchCond.getSearchKeyword(), searchCond.getSearchType()))

                .groupBy(board.id)
                .orderBy(board.id.desc());

        long total = jpaQuery.fetchCount();
        List<BoardList_OutDTO> content = jpaQuery
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return new PageImpl<>(content, pageable, total);
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
