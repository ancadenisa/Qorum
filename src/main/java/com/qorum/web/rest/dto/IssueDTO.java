package com.qorum.web.rest.dto;

import com.qorum.domain.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marian on 11/22/2015.
 */
public class IssueDTO {

    private Long id;

    private String name;

    private String content;

    private ZonedDateTime last_updated;

    private ZonedDateTime created_date;

    private Long rating;

    private Boolean is_public;

    private User user;

    private Project project;

    private List<Department> department;

    private Set<Tag> tags = new HashSet<>();

    private Long commentsNo;

    private Long views;

    private Boolean hasSolution;

    public IssueDTO() {

    }

    public IssueDTO(Issue issue) {
        this(issue.getId(), issue.getName(), issue.getContent(), issue.getLast_updated(), issue.getCreated_date(),
            issue.getRating(), issue.getIs_public(), issue.getUser(), issue.getProject(), issue.getDepartments(),
            issue.getTags(), issue.getCommentsNo(), issue.getViews(), issue.getHasSolution());
    }

    public IssueDTO(Long id, String name, String content, ZonedDateTime last_updated, ZonedDateTime created_date,
                    Long rating, Boolean is_public, User user, Project project, List<Department> department, Set<Tag> tags,
                    Long commentsNo, Long views, Boolean hasSolution) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.last_updated = last_updated;
        this.created_date = created_date;
        this.rating = rating;
        this.is_public = is_public;
        this.user = user;
        this.project = project;
        this.department = department;
        this.tags = tags;
        this.commentsNo = commentsNo;
        this.views = views;
        this.hasSolution = hasSolution;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(ZonedDateTime last_updated) {
        this.last_updated = last_updated;
    }

    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Boolean getIs_public() {
        return is_public;
    }

    public void setIs_public(Boolean is_public) {
        this.is_public = is_public;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Long getCommentsNo() {
        return commentsNo;
    }

    public void setCommentsNo(Long commentsNo) {
        this.commentsNo = commentsNo;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Boolean getHasSolution() {
        return hasSolution;
    }

    public void setHasSolution(Boolean hasSolution) {
        this.hasSolution = hasSolution;
    }
}
