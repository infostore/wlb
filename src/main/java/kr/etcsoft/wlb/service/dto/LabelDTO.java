package kr.etcsoft.wlb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.Label} entity.
 */
public class LabelDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String name;


    private Long labelGroupId;

    private String labelGroupName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLabelGroupId() {
        return labelGroupId;
    }

    public void setLabelGroupId(Long labelGroupId) {
        this.labelGroupId = labelGroupId;
    }

    public String getLabelGroupName() {
        return labelGroupName;
    }

    public void setLabelGroupName(String labelGroupName) {
        this.labelGroupName = labelGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LabelDTO labelDTO = (LabelDTO) o;
        if (labelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), labelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LabelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", labelGroupId=" + getLabelGroupId() +
            ", labelGroupName='" + getLabelGroupName() + "'" +
            "}";
    }
}
