package kr.etcsoft.wlb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.IssueAssignee} entity.
 */
public class IssueAssigneeDTO implements Serializable {
    
    private Long id;


    private Long userId;

    private Long issueId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IssueAssigneeDTO issueAssigneeDTO = (IssueAssigneeDTO) o;
        if (issueAssigneeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueAssigneeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueAssigneeDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", issueId=" + getIssueId() +
            "}";
    }
}
