package com.qorum.repository;

import com.qorum.domain.Issue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Issue entity.
 */
public interface IssueRepository extends JpaRepository<Issue,Long> {

    @Query("select issue from Issue issue where issue.user.login = ?#{principal.username}")
    List<Issue> findByUserIsCurrentUser();

}
