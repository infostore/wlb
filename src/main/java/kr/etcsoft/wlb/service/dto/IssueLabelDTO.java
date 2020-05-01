package kr.etcsoft.wlb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.IssueLabel} entity.
 */
public class IssueLabelDTO implements Serializable {
    
    private Long id;


    private Long issueId;

    private Long labelId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IssueLabelDTO issueLabelDTO = (IssueLabelDTO) o;
        if (issueLabelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueLabelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueLabelDTO{" +
            "id=" + getId() +
            ", issueId=" + getIssueId() +
            ", labelId=" + getLabelId() +
            "}";
    }
}
