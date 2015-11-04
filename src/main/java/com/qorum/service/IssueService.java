package com.qorum.service;

import com.qorum.domain.Issue;

import java.util.List;

/**
 * Created by Anca on 05-Nov-15.
 */
public interface IssueService {
    List<Issue> getIssuesByOrganization(Long orgId);

    List<Issue> getIssuesByDept(Long deptId);

    List<Issue> getIssuesByProj(Long projId);
}
