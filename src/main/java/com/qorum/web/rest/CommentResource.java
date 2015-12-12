package com.qorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.qorum.domain.Comment;
import com.qorum.domain.Issue;
import com.qorum.repository.CommentRepository;
import com.qorum.repository.IssueRepository;
import com.qorum.web.rest.dto.CommentDTO;
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
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private IssueRepository issueRepository;

    /**
     * POST  /comments -> Create a new comment.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", comment);

        if (comment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new comment cannot already have an ID").body(null);
        }
        Comment result = commentRepository.save(comment);

        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comments -> Updates an existing comment.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", comment);
        if (comment.getId() == null) {
            return createComment(comment);
        }

        if(comment.getIs_solution() != 0L) {
            Issue issueBelongingToComment = comment.getIssue();
            issueBelongingToComment.setHasSolution(true);
            issueRepository.save(issueBelongingToComment);
        }
        //update fields which are null  when coming from client side
        Comment oldComment = commentRepository.findOne(comment.getId());
        comment.setCreatedBy(oldComment.getCreatedBy());
        comment.setCreatedDate(oldComment.getCreatedDate());

        Comment result = commentRepository.save(comment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comment", comment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comments -> get all the comments.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Comment>> getAllComments(Pageable pageable)
        throws URISyntaxException {
        Page<Comment> page = commentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comments/:id -> get the "id" comment.
     */
    @RequestMapping(value = "/comments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        log.debug("REST request to get Comment : {}", id);
        return Optional.ofNullable(commentRepository.findOne(id))
            .map(comment -> new ResponseEntity<>(
                comment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comments/:id -> delete the "id" comment.
     */
    @RequestMapping(value = "/comments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comment", id.toString())).build();
    }


    /**
     * GET  /comments/:id -> get the "id" comment.
     */
    @RequestMapping(value = "/comments/byIssue/{issueId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CommentDTO>> getCommentsByIssueId(@PathVariable Long issueId) {
        log.debug("REST request to get Comments of Issue with id : {}", issueId);
        List<Comment> commentList = commentRepository.findAllByIssueId(issueId);
        Comparator<Comment> byCommentVotes = (e1, e2) -> e1.getVotes() >= e2.getVotes() ? -1 : 1;
        try {
            Comment solution = commentList.stream().filter(p -> p.getIs_solution() != 0L).findFirst().get();
            commentList.removeIf(p -> p.getIs_solution() != 0L);
            commentList = commentList.stream().sorted(byCommentVotes).collect(Collectors.toList());
            commentList.add(0, solution);
            return new ResponseEntity<List<CommentDTO>>(entityListToDto(commentList), HttpStatus.OK);
        }catch(NoSuchElementException nse){
            System.out.println("No Solution for this issue");
        }
        commentList = commentList.stream().sorted(byCommentVotes).collect(Collectors.toList());
        return new ResponseEntity<List<CommentDTO>>(entityListToDto(commentList), HttpStatus.OK);
    }


    private List<CommentDTO> entityListToDto(List<Comment> commentList){
        List<CommentDTO> commentDTOList =  new ArrayList<>();
        for(Comment comment : commentList){
            commentDTOList.add(new CommentDTO(comment));
        }
        return commentDTOList;
    }
}
