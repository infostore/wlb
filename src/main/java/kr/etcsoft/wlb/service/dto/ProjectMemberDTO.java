package kr.etcsoft.wlb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import kr.etcsoft.wlb.domain.enumeration.Role;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.ProjectMember} entity.
 */
public class ProjectMemberDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Role role;


    private Long userId;

    private Long projectId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        ProjectMemberDTO projectMemberDTO = (ProjectMemberDTO) o;
        if (projectMemberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectMemberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectMemberDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", userId=" + getUserId() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
