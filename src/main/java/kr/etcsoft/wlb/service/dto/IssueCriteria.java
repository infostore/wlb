package kr.etcsoft.wlb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import kr.etcsoft.wlb.domain.enumeration.IssueType;
import kr.etcsoft.wlb.domain.enumeration.IssueStatus;
import kr.etcsoft.wlb.domain.enumeration.Priority;
import kr.etcsoft.wlb.domain.enumeration.Resolution;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link kr.etcsoft.wlb.domain.Issue} entity. This class is used
 * in {@link kr.etcsoft.wlb.web.rest.IssueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /issues?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IssueCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IssueType
     */
    public static class IssueTypeFilter extends Filter<IssueType> {

        public IssueTypeFilter() {
        }

        public IssueTypeFilter(IssueTypeFilter filter) {
            super(filter);
        }

        @Override
        public IssueTypeFilter copy() {
            return new IssueTypeFilter(this);
        }

    }
    /**
     * Class for filtering IssueStatus
     */
    public static class IssueStatusFilter extends Filter<IssueStatus> {

        public IssueStatusFilter() {
        }

        public IssueStatusFilter(IssueStatusFilter filter) {
            super(filter);
        }

        @Override
        public IssueStatusFilter copy() {
            return new IssueStatusFilter(this);
        }

    }
    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {
        }

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }

    }
    /**
     * Class for filtering Resolution
     */
    public static class ResolutionFilter extends Filter<Resolution> {

        public ResolutionFilter() {
        }

        public ResolutionFilter(ResolutionFilter filter) {
            super(filter);
        }

        @Override
        public ResolutionFilter copy() {
            return new ResolutionFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IssueTypeFilter issueType;

    private IssueStatusFilter issueStatus;

    private PriorityFilter priority;

    private ResolutionFilter resolution;

    private InstantFilter dueDate;

    private LongFilter assigneeId;

    private LongFilter watcherId;

    private LongFilter commentId;

    private LongFilter projectId;

    private LongFilter milestoneId;

    public IssueCriteria() {
    }

    public IssueCriteria(IssueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.issueType = other.issueType == null ? null : other.issueType.copy();
        this.issueStatus = other.issueStatus == null ? null : other.issueStatus.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.resolution = other.resolution == null ? null : other.resolution.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.assigneeId = other.assigneeId == null ? null : other.assigneeId.copy();
        this.watcherId = other.watcherId == null ? null : other.watcherId.copy();
        this.commentId = other.commentId == null ? null : other.commentId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.milestoneId = other.milestoneId == null ? null : other.milestoneId.copy();
    }

    @Override
    public IssueCriteria copy() {
        return new IssueCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IssueTypeFilter getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueTypeFilter issueType) {
        this.issueType = issueType;
    }

    public IssueStatusFilter getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatusFilter issueStatus) {
        this.issueStatus = issueStatus;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public ResolutionFilter getResolution() {
        return resolution;
    }

    public void setResolution(ResolutionFilter resolution) {
        this.resolution = resolution;
    }

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public LongFilter getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(LongFilter assigneeId) {
        this.assigneeId = assigneeId;
    }

    public LongFilter getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(LongFilter watcherId) {
        this.watcherId = watcherId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(LongFilter milestoneId) {
        this.milestoneId = milestoneId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IssueCriteria that = (IssueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(issueType, that.issueType) &&
            Objects.equals(issueStatus, that.issueStatus) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(resolution, that.resolution) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(assigneeId, that.assigneeId) &&
            Objects.equals(watcherId, that.watcherId) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(milestoneId, that.milestoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        issueType,
        issueStatus,
        priority,
        resolution,
        dueDate,
        assigneeId,
        watcherId,
        commentId,
        projectId,
        milestoneId
        );
    }

    @Override
    public String toString() {
        return "IssueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (issueType != null ? "issueType=" + issueType + ", " : "") +
                (issueStatus != null ? "issueStatus=" + issueStatus + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (resolution != null ? "resolution=" + resolution + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (assigneeId != null ? "assigneeId=" + assigneeId + ", " : "") +
                (watcherId != null ? "watcherId=" + watcherId + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (milestoneId != null ? "milestoneId=" + milestoneId + ", " : "") +
            "}";
    }

}
