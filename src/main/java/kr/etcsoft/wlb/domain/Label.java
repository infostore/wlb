package kr.etcsoft.wlb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Label.
 */
@Entity
@Table(name = "label")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "name", length = 10, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "label")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IssueLabel> issueLabels = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("labels")
    private LabelGroup labelGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Label name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IssueLabel> getIssueLabels() {
        return issueLabels;
    }

    public Label issueLabels(Set<IssueLabel> issueLabels) {
        this.issueLabels = issueLabels;
        return this;
    }

    public Label addIssueLabel(IssueLabel issueLabel) {
        this.issueLabels.add(issueLabel);
        issueLabel.setLabel(this);
        return this;
    }

    public Label removeIssueLabel(IssueLabel issueLabel) {
        this.issueLabels.remove(issueLabel);
        issueLabel.setLabel(null);
        return this;
    }

    public void setIssueLabels(Set<IssueLabel> issueLabels) {
        this.issueLabels = issueLabels;
    }

    public LabelGroup getLabelGroup() {
        return labelGroup;
    }

    public Label labelGroup(LabelGroup labelGroup) {
        this.labelGroup = labelGroup;
        return this;
    }

    public void setLabelGroup(LabelGroup labelGroup) {
        this.labelGroup = labelGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Label)) {
            return false;
        }
        return id != null && id.equals(((Label) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Label{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
