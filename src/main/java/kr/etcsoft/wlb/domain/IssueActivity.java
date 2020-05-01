package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IssueActivity.
 */
@Entity
@Table(name = "issue_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 4000)
    @Column(name = "activity", length = 4000, nullable = false)
    private String activity;

    @ManyToOne
    @JsonIgnoreProperties("issueActivities")
    private Issue issue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public IssueActivity activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Issue getIssue() {
        return issue;
    }

    public IssueActivity issue(Issue issue) {
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
        if (!(o instanceof IssueActivity)) {
            return false;
        }
        return id != null && id.equals(((IssueActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IssueActivity{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
