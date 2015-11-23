package com.qorum.repository;

import com.qorum.domain.Comment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select comment from Comment comment where comment.user.login = ?#{principal.username}")
    List<Comment> findByUserIsCurrentUser();

    @Query("select comment from Comment comment where comment.issue.id = :issueId")
    List<Comment> findAllByIssueId(@Param("issueId") Long issueId);

    @Query("select count(comment) from Comment comment where comment.issue.id = :issueId")
    Long countByIssueId(@Param("issueId") Long issueId);
}
