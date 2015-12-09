package com.qorum.repository;

import com.qorum.domain.Issue;

import com.qorum.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the Issue entity.
 */
public interface IssueRepository extends JpaRepository<Issue,Long> {

    @Query("select issue from Issue issue where issue.user.login = ?#{principal.username}")
    List<Issue> findByUserIsCurrentUser();

    @Query("select distinct issue from Issue issue left join fetch issue.tags")
    List<Issue> findAllWithEagerRelationships();

    @Query("select issue from Issue issue left join fetch issue.tags left join fetch issue.departments where issue.id =:id")
    Issue findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct issue from Issue issue, Department department join fetch issue.departments department where department.organization.id = :organizationId")
    List<Issue> getIssuesByOrganization(@Param("organizationId") Long orgId);

    @Query("select distinct issue from Issue issue, Department department  join fetch issue.departments department where department.id = :deptId")
    List<Issue> getIssuesByDept(@Param("deptId") Long deptId);

    @Query("select distinct issue from Issue issue where issue.project.id = :projId")
    List<Issue> getIssuesByProj(@Param("projId") Long projId);

    @Query("select count(issue) from Issue issue")
    Long getCount();

    @Query("select distinct a from Issue a join a.tags t where t in (:tags)")
    List<Issue> getFilteredByTags(@Param("tags") List<Tag> tags);


    @Modifying
    @Transactional
    @Query("update Issue issue set issue.views = issue.views+1 where issue.id = :issueId")
    void increaseViews(@Param("issueId") Long issueId);

    /*Logged user queries*/
    @Query("select distinct issue from Issue issue where lower(issue.name) like :issueName")
    Page<Issue> getFilteredByNamePage(Pageable pageable, @Param("issueName") String issueName);

    @Query("select distinct issue from Issue issue join issue.tags t where t in (:tags) and lower(issue.name) like :issueName")
    Page<Issue> getFilteredByNameAndTagsPage(Pageable pageable, @Param("tags") List<Tag> tags, @Param("issueName") String issueName);

    /* Public queries */
    @Query("select distinct issue from Issue issue where lower(issue.name) like :issueName and issue.is_public=true")
    Page<Issue> getPublicFilteredByNamePage(Pageable pageable, @Param("issueName") String issueName);

    @Query("select distinct issue from Issue issue join issue.tags t where t in (:tags) and lower(issue.name) like :issueName and issue.is_public=true")
    Page<Issue> getPublicFilteredByNameAndTagsPage(Pageable pageable, @Param("tags") List<Tag> tags, @Param("issueName") String issueName);

}
