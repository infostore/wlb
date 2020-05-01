package kr.etcsoft.wlb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.IssueActivity} entity.
 */
public class IssueActivityDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 4000)
    private String activity;


    private Long issueId;
    
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

        IssueActivityDTO issueActivityDTO = (IssueActivityDTO) o;
        if (issueActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueActivityDTO{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            ", issueId=" + getIssueId() +
            "}";
    }
}
