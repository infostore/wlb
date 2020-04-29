package kr.etcsoft.wlb.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import kr.etcsoft.wlb.domain.enumeration.IssueType;
import kr.etcsoft.wlb.domain.enumeration.IssueStatus;
import kr.etcsoft.wlb.domain.enumeration.Priority;
import kr.etcsoft.wlb.domain.enumeration.Resolution;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.Issue} entity.
 */
public class IssueDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Lob
    private String description;

    @NotNull
    private IssueType issueType;

    @NotNull
    private IssueStatus issueStatus;

    @NotNull
    private Priority priority;

    private Resolution resolution;

    private Instant dueDate;


    private Long projectId;

    private String projectName;

    private Long milestoneId;

    private String milestoneName;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IssueDTO issueDTO = (IssueDTO) o;
        if (issueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", issueType='" + getIssueType() + "'" +
            ", issueStatus='" + getIssueStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", projectId=" + getProjectId() +
            ", projectName='" + getProjectName() + "'" +
            ", milestoneId=" + getMilestoneId() +
            ", milestoneName='" + getMilestoneName() + "'" +
            "}";
    }
}
