package kr.etcsoft.wlb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.IssueWatcher} entity.
 */
public class IssueWatcherDTO implements Serializable {
    
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

        IssueWatcherDTO issueWatcherDTO = (IssueWatcherDTO) o;
        if (issueWatcherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueWatcherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueWatcherDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", issueId=" + getIssueId() +
            "}";
    }
}
