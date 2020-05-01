package kr.etcsoft.wlb.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectMember> projectMembers = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectActivity> projectActivities = new HashSet<>();

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

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectMember> getProjectMembers() {
        return projectMembers;
    }

    public Project projectMembers(Set<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
        return this;
    }

    public Project addProjectMember(ProjectMember projectMember) {
        this.projectMembers.add(projectMember);
        projectMember.setProject(this);
        return this;
    }

    public Project removeProjectMember(ProjectMember projectMember) {
        this.projectMembers.remove(projectMember);
        projectMember.setProject(null);
        return this;
    }

    public void setProjectMembers(Set<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public Set<ProjectActivity> getProjectActivities() {
        return projectActivities;
    }

    public Project projectActivities(Set<ProjectActivity> projectActivities) {
        this.projectActivities = projectActivities;
        return this;
    }

    public Project addProjectActivity(ProjectActivity projectActivity) {
        this.projectActivities.add(projectActivity);
        projectActivity.setProject(this);
        return this;
    }

    public Project removeProjectActivity(ProjectActivity projectActivity) {
        this.projectActivities.remove(projectActivity);
        projectActivity.setProject(null);
        return this;
    }

    public void setProjectActivities(Set<ProjectActivity> projectActivities) {
        this.projectActivities = projectActivities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
