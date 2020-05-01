package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IssueLabel.
 */
@Entity
@Table(name = "issue_label")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IssueLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("issueLabels")
    private Issue issue;

    @ManyToOne
    @JsonIgnoreProperties("issueLabels")
    private Label label;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public IssueLabel issue(Issue issue) {
        this.issue = issue;
        return this;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Label getLabel() {
        return label;
    }

    public IssueLabel label(Label label) {
        this.label = label;
        return this;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueLabel)) {
            return false;
        }
        return id != null && id.equals(((IssueLabel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IssueLabel{" +
            "id=" + getId() +
            "}";
    }
}
