package com.qorum.service;

import com.qorum.domain.Department;
import com.qorum.domain.Issue;
import com.qorum.domain.Organization;
import com.qorum.domain.Tag;
import com.qorum.web.rest.dto.IssueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Anca on 05-Nov-15.
 */
public interface IssueService {
    List<Issue> getIssuesByOrganization(Long orgId);

    List<Issue> getIssuesByDept(Long deptId);

    List<Issue> getIssuesByProj(Long projId);

    Page<Issue> findAll(Pageable pageable);

    Long getCount();

    Page<Issue> getFilteredByNameAndTagsPage(Pageable pageable, List<Tag> tags, String issueName);

    Page<Issue> getFilteredByNamePage(Pageable pageable, String issueName);

    Page<Issue> getFilteredByNameAndOrganizationsPage(Pageable pageable, List<Organization> orgs, String issueName);

    Page<Issue> getFilteredByNameAndTagsAndOrganizationsPage(Pageable pageable, List<Tag> tags, List<Organization> orgs, String issueName);


    Page<Issue> getPublicFilteredByNameAndTagsPage(Pageable pageable, List<Tag> tags, String issueName);

    Page<Issue> getPublicFilteredByNamePage(Pageable pageable, String issueName);

    Page<Issue> getPublicFilteredByNameAndOrganizationsPage(Pageable pageable, List<Organization> orgs, String issueName);

    Page<Issue> getPublicFilteredByNameAndTagsAndOrganizationsPage(Pageable pageable, List<Tag> tags, List<Organization> orgs, String issueName);

    void increaseViews(Long issueId);
}
