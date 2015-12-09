package com.qorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qorum.domain.Issue;
import com.qorum.domain.Tag;
import com.qorum.repository.IssueRepository;
import com.qorum.service.IssueService;
import com.qorum.web.rest.dto.IssueDTO;
import com.qorum.web.rest.util.HeaderUtil;
import com.qorum.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Issue.
 */
@RestController
@RequestMapping("/api")
public class IssueResource {

    private final Logger log = LoggerFactory.getLogger(IssueResource.class);

    @Inject
    private IssueRepository issueRepository;

    @Inject
    private IssueService issueService;

    /**
     * POST  /issues -> Create a new issue.
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) throws URISyntaxException {
        log.debug("REST request to save Issue : {}", issue);
        if (issue.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new issue cannot already have an ID").body(null);
        }
        Issue result = issueRepository.save(issue);
        return ResponseEntity.created(new URI("/api/issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("issue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /issues -> Updates an existing issue.
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Issue> updateIssue(@RequestBody Issue issue) throws URISyntaxException {
        log.debug("REST request to update Issue : {}", issue);
        if (issue.getId() == null) {
            return createIssue(issue);
        }
        Issue result = issueRepository.save(issue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("issue", issue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /issues -> get all the issues.
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IssueDTO>> getAllIssues(Pageable pageable)
        throws URISyntaxException {
        Page<Issue> page = issueService.findAll(pageable);
        List<IssueDTO> issues = page.getContent().stream()
            .map(issue -> new IssueDTO(issue))
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/issues");
        return new ResponseEntity<>(issues, headers, HttpStatus.OK);
    }

    /**
     * GET  /issues -> get all the issues.
     */
    /*@RequestMapping(value = "/issues/filtered",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Issue>> getAllIssuesFiltered(@RequestParam(value = "issueName") String issueName, @RequestBody List<Tag> tags)
        throws URISyntaxException {
        //Page<Issue> page = issueService.findAll(pageable);
        *//*List<IssueDTO> issues = page.getContent().stream()
            .map(issue -> new IssueDTO(issue))
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/issues");*//*
        issueName = "%" + issueName.toLowerCase() + "%";
        List<Issue> issues = issueService.getFilteredByNameAndTags(tags, issueName);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }*/

    /**
     * GET  /issues -> get all the issues.
     */
    @RequestMapping(value = "/issues/filtered",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Issue>> getAllIssuesFiltered(Pageable pageable, @RequestParam(value = "issueName") String issueName, @RequestBody List<Tag> tags)
        throws URISyntaxException {

        issueName = "%" + issueName.toLowerCase() + "%";
        Page<Issue> page = null;

        if (tags != null && tags.size() > 0) {
            page = issueService.getFilteredByNameAndTagsPage(pageable,tags,issueName);
        }
        else {
            page = issueService.getFilteredByNamePage(pageable,issueName);
        }

        /*List<IssueDTO> issues = page.getContent().stream()
            .map(issue -> new IssueDTO(issue))
            .collect(Collectors.toList());*/
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/issues");
        //issueName = "%" + issueName.toLowerCase() + "%";
        //List<Issue> issues = issueService.getFilteredByNameAndTags(tags,issueName);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /issues/:id -> get the "id" issue.
     */
    @RequestMapping(value = "/issues/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Issue> getIssue(@PathVariable Long id) {
        log.debug("REST request to get Issue : {}", id);
        return Optional.ofNullable(issueRepository.findOneWithEagerRelationships(id))
            .map(issue -> new ResponseEntity<>(
                issue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /issues/:id -> delete the "id" issue.
     */
    @RequestMapping(value = "/issues/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        log.debug("REST request to delete Issue : {}", id);
        issueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("issue", id.toString())).build();
    }


    @RequestMapping(value = "/issuesByOrg/{orgId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Issue>> getIssuesByOrganization(@PathVariable Long orgId,Pageable pageable)
        throws URISyntaxException {
        List<Issue> issues = issueService.getIssuesByOrganization(orgId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }


    @RequestMapping(value = "/issuesByDep/{depId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Issue>> getIssuesByDepartment(@PathVariable Long depId,Pageable pageable)
        throws URISyntaxException {
        List<Issue> issues = issueService.getIssuesByDept(depId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @RequestMapping(value = "/issuesByProj/{projId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Issue>> getIssuesByProj(@PathVariable Long projId,Pageable pageable)
        throws URISyntaxException {
        List<Issue> issues = issueService.getIssuesByProj(projId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }


    @RequestMapping(value = "/issues/getCount",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Long> getIssuesCount()
        throws URISyntaxException {
        Long issuesCount = issueService.getCount();
        return new ResponseEntity<>(issuesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/issues/increaseViews/{issueId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void increaseViews(@PathVariable Long issueId)
        throws URISyntaxException {
        issueService.increaseViews(issueId);
    }
}
