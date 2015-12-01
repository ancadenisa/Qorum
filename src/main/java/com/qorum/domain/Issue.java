package com.qorum.domain;

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

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @ManyToMany    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "issue_department",
        joinColumns = @JoinColumn(name="issue_id", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="department_id", referencedColumnName="ID"))
    private List<Department> departments =  new ArrayList<>();

    @ManyToMany    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "issue_tag",
               joinColumns = @JoinColumn(name="issues_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="ID"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", cascade =  CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> commentSet;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Issue issue = (Issue) o;

        if ( ! Objects.equals(id, issue.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Issue{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", content='" + content + "'" +
            ", last_updated='" + last_updated + "'" +
            ", created_date='" + created_date + "'" +
            ", rating='" + rating + "'" +
            ", is_public='" + is_public + "'" +
            '}';
    }
}
