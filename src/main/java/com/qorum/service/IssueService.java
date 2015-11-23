package com.qorum.service;

import com.qorum.domain.Issue;
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

    List<Issue> getFilteredByNameAndTags(List<Tag> tags, String issueName);

    Page<Issue> getFilteredByNameAndTagsPage(Pageable pageable, List<Tag> tags, String issueName);

}
