package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import kr.etcsoft.wlb.domain.enumeration.IssueType;

import kr.etcsoft.wlb.domain.enumeration.IssueStatus;

import kr.etcsoft.wlb.domain.enumeration.Priority;

import kr.etcsoft.wlb.domain.enumeration.Resolution;

/**
 * A Issue.
 */
@Entity
@Table(name = "issue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "issue_type", nullable = false)
    private IssueType issueType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "issue_status", nullable = false)
    private IssueStatus issueStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "resolution")
    private Resolution resolution;

    @Column(name = "due_date")
    private Instant dueDate;

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueAssignee> assignees = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueWatcher> watchers = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueLabel> issueLabels = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueAttachment> issueAttachments = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueActivity> issueActivities = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("issues")
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties("issues")
    private Milestone milestone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Issue name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Issue description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public Issue issueType(IssueType issueType) {
        this.issueType = issueType;
        return this;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public Issue issueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
        return this;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public Issue priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Issue resolution(Resolution resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public Issue dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Set<IssueAssignee> getAssignees() {
        return assignees;
    }

    public Issue assignees(Set<IssueAssignee> issueAssignees) {
        this.assignees = issueAssignees;
        return this;
    }

    public Issue addAssignee(IssueAssignee issueAssignee) {
        this.assignees.add(issueAssignee);
        issueAssignee.setIssue(this);
        return this;
    }

    public Issue removeAssignee(IssueAssignee issueAssignee) {
        this.assignees.remove(issueAssignee);
        issueAssignee.setIssue(null);
        return this;
    }

    public void setAssignees(Set<IssueAssignee> issueAssignees) {
        this.assignees = issueAssignees;
    }

    public Set<IssueWatcher> getWatchers() {
        return watchers;
    }

    public Issue watchers(Set<IssueWatcher> issueWatchers) {
        this.watchers = issueWatchers;
        return this;
    }

    public Issue addWatcher(IssueWatcher issueWatcher) {
        this.watchers.add(issueWatcher);
        issueWatcher.setIssue(this);
        return this;
    }

    public Issue removeWatcher(IssueWatcher issueWatcher) {
        this.watchers.remove(issueWatcher);
        issueWatcher.setIssue(null);
        return this;
    }

    public void setWatchers(Set<IssueWatcher> issueWatchers) {
        this.watchers = issueWatchers;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Issue comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Issue addComment(Comment comment) {
        this.comments.add(comment);
        comment.setIssue(this);
        return this;
    }

    public Issue removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setIssue(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<IssueLabel> getIssueLabels() {
        return issueLabels;
    }

    public Issue issueLabels(Set<IssueLabel> issueLabels) {
        this.issueLabels = issueLabels;
        return this;
    }

    public Issue addIssueLabel(IssueLabel issueLabel) {
        this.issueLabels.add(issueLabel);
        issueLabel.setIssue(this);
        return this;
    }

    public Issue removeIssueLabel(IssueLabel issueLabel) {
        this.issueLabels.remove(issueLabel);
        issueLabel.setIssue(null);
        return this;
    }

    public void setIssueLabels(Set<IssueLabel> issueLabels) {
        this.issueLabels = issueLabels;
    }

    public Set<IssueAttachment> getIssueAttachments() {
        return issueAttachments;
    }

    public Issue issueAttachments(Set<IssueAttachment> issueAttachments) {
        this.issueAttachments = issueAttachments;
        return this;
    }

    public Issue addIssueAttachment(IssueAttachment issueAttachment) {
        this.issueAttachments.add(issueAttachment);
        issueAttachment.setIssue(this);
        return this;
    }

    public Issue removeIssueAttachment(IssueAttachment issueAttachment) {
        this.issueAttachments.remove(issueAttachment);
        issueAttachment.setIssue(null);
        return this;
    }

    public void setIssueAttachments(Set<IssueAttachment> issueAttachments) {
        this.issueAttachments = issueAttachments;
    }

    public Set<IssueActivity> getIssueActivities() {
        return issueActivities;
    }

    public Issue issueActivities(Set<IssueActivity> issueActivities) {
        this.issueActivities = issueActivities;
        return this;
    }

    public Issue addIssueActivity(IssueActivity issueActivity) {
        this.issueActivities.add(issueActivity);
        issueActivity.setIssue(this);
        return this;
    }

    public Issue removeIssueActivity(IssueActivity issueActivity) {
        this.issueActivities.remove(issueActivity);
        issueActivity.setIssue(null);
        return this;
    }

    public void setIssueActivities(Set<IssueActivity> issueActivities) {
        this.issueActivities = issueActivities;
    }

    public Project getProject() {
        return project;
    }

    public Issue project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public Issue milestone(Milestone milestone) {
        this.milestone = milestone;
        return this;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Issue)) {
            return false;
        }
        return id != null && id.equals(((Issue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Issue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", issueType='" + getIssueType() + "'" +
            ", issueStatus='" + getIssueStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
