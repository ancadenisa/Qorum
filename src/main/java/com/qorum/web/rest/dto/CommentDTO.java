package com.qorum.web.rest.dto;

import com.qorum.domain.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by Anca on 27-Nov-15.
 */
public class CommentDTO {
    private Long id;

    private String content;

    private Long is_solution;

    private User user;

    private Issue issue;

    private String createdBy;

    private ZonedDateTime createdDate = ZonedDateTime.now();

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    public CommentDTO(Long id, String content, Long is_solution, User user, Issue issue, String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.is_solution = is_solution;
        this.user = user;
        this.issue = issue;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public CommentDTO(Comment comment){
        this(comment.getId(), comment.getContent(), comment.getIs_solution(), comment.getUser(), comment.getIssue(), comment.getCreatedBy(),
            comment.getCreatedDate(), comment.getLastModifiedBy(), comment.getLastModifiedDate());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getIs_solution() {
        return is_solution;
    }

    public void setIs_solution(Long is_solution) {
        this.is_solution = is_solution;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
