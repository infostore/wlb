package kr.etcsoft.wlb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.ProjectActivity} entity.
 */
public class ProjectActivityDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 4000)
    private String activity;


    private Long projectId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectActivityDTO projectActivityDTO = (ProjectActivityDTO) o;
        if (projectActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectActivityDTO{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            ", projectId=" + getProjectId() +
            "}";
    }
}
