package com.qorum.repository;

import com.qorum.domain.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select comment from Comment comment where comment.user.id = :userId")
    List<Comment> findCommentByUserAuthor(@Param("userId") Long userId);

    @Query("select comment from Comment comment join comment.issue where comment.issue.is_public=true and comment.is_solution=1")
    Page<Comment> findPublicSolutions(Pageable pageable);

    @Query("select comment from Comment comment join comment.issue where comment.is_solution=1")
    Page<Comment> findSolutions(Pageable pageable);
}
