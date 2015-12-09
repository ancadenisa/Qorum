package com.qorum.service.impl;

import com.qorum.domain.Issue;
import com.qorum.domain.Tag;
import com.qorum.repository.CommentRepository;
import com.qorum.repository.IssueRepository;
import com.qorum.service.IssueService;
import com.qorum.web.rest.dto.IssueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Inject
    CommentRepository commentRepository;

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

    @Override
    public Page<Issue> findAll(Pageable pageable) {

        Page<Issue> issuesPage = issueRepository.findAll(pageable);
        List<Issue> issues = issuesPage.getContent();
        for (Issue issue : issues) {
            issue.setCommentsNo(commentRepository.countByIssueId(issue.getId()));
        }

        return issuesPage;
    }

    @Override
    public Long getCount() {
        return issueRepository.getCount();
    }

    @Override
    public Page<Issue> getFilteredByNameAndTagsPage(Pageable pageable, List<Tag> tags, String issueName) {

        Page<Issue> issuesPage = issueRepository.getFilteredByNameAndTagsPage(pageable, tags, issueName);
        List<Issue> issues = issuesPage.getContent();
        for (Issue issue : issues) {
            issue.setCommentsNo(commentRepository.countByIssueId(issue.getId()));
        }

        return issuesPage;
    }

    @Override
    public Page<Issue> getFilteredByNamePage(Pageable pageable, String issueName) {

        Page<Issue> issuesPage = issueRepository.getFilteredByNamePage(pageable, issueName);
        List<Issue> issues = issuesPage.getContent();
        for (Issue issue : issues) {
            issue.setCommentsNo(commentRepository.countByIssueId(issue.getId()));
        }

        return issuesPage;
    }

    @Override
    public Page<Issue> getPublicFilteredByNameAndTagsPage(Pageable pageable, List<Tag> tags, String issueName) {
        Page<Issue> issuesPage = issueRepository.getPublicFilteredByNameAndTagsPage(pageable, tags, issueName);
        List<Issue> issues = issuesPage.getContent();
        for (Issue issue : issues) {
            issue.setCommentsNo(commentRepository.countByIssueId(issue.getId()));
        }

        return issuesPage;
    }

    @Override
    public Page<Issue> getPublicFilteredByNamePage(Pageable pageable, String issueName) {
        Page<Issue> issuesPage = issueRepository.getPublicFilteredByNamePage(pageable, issueName);
        List<Issue> issues = issuesPage.getContent();
        for (Issue issue : issues) {
            issue.setCommentsNo(commentRepository.countByIssueId(issue.getId()));
        }

        return issuesPage;
    }


    @Override
    public void increaseViews(Long issueId) {
        issueRepository.increaseViews(issueId);
    }
}
