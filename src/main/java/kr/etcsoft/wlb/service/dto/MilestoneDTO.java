package kr.etcsoft.wlb.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import kr.etcsoft.wlb.domain.enumeration.MilestoneStatus;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.Milestone} entity.
 */
public class MilestoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Lob
    private String description;

    private MilestoneStatus milestoneStatus;

    private Instant dueDate;

    private Long projectId;

    private String projectName;

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

    public MilestoneStatus getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(MilestoneStatus milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MilestoneDTO milestoneDTO = (MilestoneDTO) o;
        if (milestoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), milestoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MilestoneDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", milestoneStatus='" + getMilestoneStatus() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", projectId=" + getProjectId() +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
