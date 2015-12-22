package com.qorum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;

/**
 * A Issue.
 */
@Entity
@Table(name = "issue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "last_updated")
    private ZonedDateTime last_updated;

    @Column(name = "created_date")
    private ZonedDateTime created_date;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "is_public")
    private Boolean is_public;

    @Column(name = "views")
    private Long views;

    @Column(name = "has_solution")
    private Boolean hasSolution;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "issue_department",
        joinColumns = @JoinColumn(name = "issue_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "ID"))
    private List<Department> departments = new ArrayList<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "issue_tag",
        joinColumns = @JoinColumn(name = "issues_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "ID"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> commentSet = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "vote_user_issue",
            joinColumns = @JoinColumn(name="issue_id", referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="ID"))
    private Set<User> users = new HashSet<>();

    @Transient
    private Long commentsNo;

    public Long getCommentsNo() {
        return commentsNo;
    }

    public void setCommentsNo(Long commentsNo) {
        this.commentsNo = commentsNo;
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (!getId().equals(issue.getId())) return false;
        if (getName() != null ? !getName().equals(issue.getName()) : issue.getName() != null) return false;
        if (getContent() != null ? !getContent().equals(issue.getContent()) : issue.getContent() != null) return false;
        if (getLast_updated() != null ? !getLast_updated().equals(issue.getLast_updated()) : issue.getLast_updated() != null)
            return false;
        if (getCreated_date() != null ? !getCreated_date().equals(issue.getCreated_date()) : issue.getCreated_date() != null)
            return false;
        if (getRating() != null ? !getRating().equals(issue.getRating()) : issue.getRating() != null) return false;
        if (getIs_public() != null ? !getIs_public().equals(issue.getIs_public()) : issue.getIs_public() != null)
            return false;
        if (getUser() != null ? !getUser().equals(issue.getUser()) : issue.getUser() != null) return false;
        if (getProject() != null ? !getProject().equals(issue.getProject()) : issue.getProject() != null) return false;
        if (getDepartments() != null ? !getDepartments().equals(issue.getDepartments()) : issue.getDepartments() != null)
            return false;
        if (getTags() != null ? !getTags().equals(issue.getTags()) : issue.getTags() != null) return false;
        if (getCommentSet() != null ? !getCommentSet().equals(issue.getCommentSet()) : issue.getCommentSet() != null)
            return false;
        if (getViews() != null ? !getViews().equals(issue.getViews()) : issue.getViews() != null) return false;
        if (getHasSolution() != null ? !getHasSolution().equals(issue.getHasSolution()) : issue.getHasSolution() != null)
            return false;

        return !(getCommentsNo() != null ? !getCommentsNo().equals(issue.getCommentsNo()) : issue.getCommentsNo() != null);

    }


    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getLast_updated() != null ? getLast_updated().hashCode() : 0);
        result = 31 * result + (getCreated_date() != null ? getCreated_date().hashCode() : 0);
        result = 31 * result + (getRating() != null ? getRating().hashCode() : 0);
        result = 31 * result + (getIs_public() != null ? getIs_public().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getProject() != null ? getProject().hashCode() : 0);
        result = 31 * result + (getDepartments() != null ? getDepartments().hashCode() : 0);
        result = 31 * result + (getCommentsNo() != null ? getCommentsNo().hashCode() : 0);
        result = 31 * result + (getViews() != null ? getViews().hashCode() : 0);
        result = 31 * result + (getHasSolution() != null ? getHasSolution().hashCode():0);

        return result;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }
}
