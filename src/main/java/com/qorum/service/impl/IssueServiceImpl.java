package com.qorum.service.impl;

import com.qorum.domain.Issue;
import com.qorum.repository.IssueRepository;
import com.qorum.service.IssueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Anca on 05-Nov-15.
 */
@Service
@Transactional
public class IssueServiceImpl implements IssueService {
    @Inject
    IssueRepository issueRepository;
    @Override
    public List<Issue> getIssuesByOrganization(Long orgId) {
        return issueRepository.getIssuesByOrganization(orgId);
    }

    @Override
    public List<Issue> getIssuesByDept(Long deptId) {
        return issueRepository.getIssuesByDept(deptId);
    }

    @Override
    public List<Issue> getIssuesByProj(Long projId) {
        return issueRepository.getIssuesByProj(projId);
    }
}
