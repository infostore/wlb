package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IssueAttachment.
 */
@Entity
@Table(name = "issue_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("issueAttachments")
    private Attachment attachment;

    @ManyToOne
    @JsonIgnoreProperties("issueAttachments")
    private Issue issue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public IssueAttachment attachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Issue getIssue() {
        return issue;
    }

    public IssueAttachment issue(Issue issue) {
        this.issue = issue;
        return this;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueAttachment)) {
            return false;
        }
        return id != null && id.equals(((IssueAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IssueAttachment{" +
            "id=" + getId() +
            "}";
    }
}
