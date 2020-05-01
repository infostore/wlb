package kr.etcsoft.wlb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.IssueAttachment} entity.
 */
public class IssueAttachmentDTO implements Serializable {
    
    private Long id;


    private Long attachmentId;

    private Long issueId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
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

        IssueAttachmentDTO issueAttachmentDTO = (IssueAttachmentDTO) o;
        if (issueAttachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issueAttachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IssueAttachmentDTO{" +
            "id=" + getId() +
            ", attachmentId=" + getAttachmentId() +
            ", issueId=" + getIssueId() +
            "}";
    }
}
