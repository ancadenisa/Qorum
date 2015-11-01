package com.qorum.repository;

import com.qorum.domain.Issue;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Issue entity.
 */
public interface IssueRepository extends JpaRepository<Issue,Long> {

    @Query("select issue from Issue issue where issue.user.login = ?#{principal.username}")
    List<Issue> findByUserIsCurrentUser();

    @Query("select distinct issue from Issue issue left join fetch issue.tags")
    List<Issue> findAllWithEagerRelationships();

    @Query("select issue from Issue issue left join fetch issue.tags where issue.id =:id")
    Issue findOneWithEagerRelationships(@Param("id") Long id);

}
