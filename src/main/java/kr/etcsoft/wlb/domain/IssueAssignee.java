package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IssueAssignee.
 */
@Entity
@Table(name = "issue_assignee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueAssignee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("issueAssignees")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("assignees")
    private Issue issue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public IssueAssignee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Issue getIssue() {
        return issue;
    }

    public IssueAssignee issue(Issue issue) {
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
        if (!(o instanceof IssueAssignee)) {
            return false;
        }
        return id != null && id.equals(((IssueAssignee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IssueAssignee{" +
            "id=" + getId() +
            "}";
    }
}
